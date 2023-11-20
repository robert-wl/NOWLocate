package edu.bluejack23_1.nowlocate.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.ChatDoc
import edu.bluejack23_1.nowlocate.models.MessageDoc
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.service.FirebaseNotificationService
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID

class ChatRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("chats")
    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()
    private val firebaseNotificationService = FirebaseNotificationService()

    private suspend fun chatExists(id1: String, id2: String): Chat? {
        val chatQuery = collection
            .where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("person1", id1),
                        Filter.equalTo("person2", id2)
                    ),
                    Filter.and(
                        Filter.equalTo("person1", id2),
                        Filter.equalTo("person2", id1)
                    )
                )
            ).limit(1).get().await()

        if (chatQuery.isEmpty) {
            return null
        }
        val chatDoc = chatQuery.documents[0].toObject(ChatDoc::class.java)!!
        val sender = authRepository.getCurrentUser()
        return docToChat(chatDoc, sender)
    }

    suspend fun addChat(id1: String, id2: String): Chat {
        val result = chatExists(id1, id2)

        if (result != null) {
            return result
        }

        val chatDoc = ChatDoc(UUID.randomUUID().toString(), id1, id2,"", Date())
        collection.document(chatDoc.id).set(chatDoc).await()

        val sender = authRepository.getCurrentUser()
        return docToChat(chatDoc, sender)
    }

    fun sendMessage(chat: Chat, message: MessageDoc) {
        val batch = FirebaseFirestore.getInstance().batch()
        val chatRef = collection.document(chat.id)
        val messagesRef = chatRef.collection("messages").document(message.id)
        batch.set(messagesRef, message)
        batch.update(chatRef, mapOf(
            "lastMessage" to message.message,
            "lastTime" to FieldValue.serverTimestamp()
        ))
        Log.wtf("a", chat.sender.token)
        batch.commit().addOnSuccessListener {
            Log.wtf("PUSH NOTIFICATION1", chat.recipient.token)
            Log.wtf("PUSH NOTIFICATION2", chat.sender.token)
            firebaseNotificationService.pushChatNotification(
                chat.sender.token,
                "${chat.sender.firstName} ${chat.sender.lastName}",
                message.message,
                chat.recipient.id
            )
        }
    }

    suspend fun docToChat(chatDoc: ChatDoc, sender: User): Chat {
        val recipientId = if(chatDoc.person1 == sender.id) chatDoc.person2 else chatDoc.person1
        val recipient = userRepository.getUser(recipientId).getOrNull()
        return Chat(
            chatDoc.id,
            sender,
            recipient!!,
            chatDoc.lastMessage,
            chatDoc.lastTime
        )
    }


    fun addRealTimeConversationListener(id: String, chatData: MutableLiveData<ArrayList<ChatDoc>>) {
        val chatDocs = ArrayList<ChatDoc>()
        collection
            .where(
                Filter.or(
                    Filter.equalTo("person1", id),
                    Filter.equalTo("person2", id)
                )
            )
            .addSnapshotListener { value, e ->
                value?.let { snapshot ->
                    for (documentChange in snapshot.documentChanges) {
                        val chatDoc = documentChange.document.toObject(ChatDoc::class.java)
                        when (documentChange.type) {
                            DocumentChange.Type.ADDED -> {
                                chatDocs.add(chatDoc)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val index = chatDocs.indexOfFirst { it.id == chatDoc.id }
                                if (index != -1) {
                                    chatDocs[index] = chatDoc
                                }
                            }
                            DocumentChange.Type.REMOVED -> {
                                chatDocs.removeAll { it.id == chatDoc.id }
                            }
                        }
                    }
                    chatData.value = ArrayList(chatDocs)
                }
            }
    }

    fun addRealTimeMessageListener(id: String, messageData: MutableLiveData<ArrayList<MessageDoc>>){
        val messageDocs = ArrayList<MessageDoc>()
        collection.document(id).collection("messages")
            .addSnapshotListener { value, e ->
                value?.let { snapshot ->
                    for (documentChange in snapshot.documentChanges) {
                        val messageDoc = documentChange.document.toObject(MessageDoc::class.java)
                        messageDoc.id = documentChange.document.id
                        when (documentChange.type) {
                            DocumentChange.Type.ADDED -> {
                                messageDocs.add(messageDoc)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val index = messageDocs.indexOfFirst { it.id == messageDoc.id }
                                if (index != -1) {
                                    messageDocs[index] = messageDoc
                                }
                            }
                            DocumentChange.Type.REMOVED -> {
                                messageDocs.removeAll { it.id == messageDoc.id }
                            }
                        }
                    }
                    messageData.value = ArrayList(messageDocs)
                }
            }
    }


}