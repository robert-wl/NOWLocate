package edu.bluejack23_1.nowlocate.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.ChatDoc
import edu.bluejack23_1.nowlocate.models.Message
import edu.bluejack23_1.nowlocate.models.User
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID

class ChatRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("chats")
    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()

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

        val chatDoc = ChatDoc(UUID.randomUUID().toString(), id1, id2, ArrayList(), "", Date())
        collection.document(chatDoc.id).set(chatDoc).await()

        val sender = authRepository.getCurrentUser()
        return docToChat(chatDoc, sender)
    }

    fun sendMessage(id: String, message: Message) {
        collection.document(id).collection("messages").add(message)
        collection.document(id).update("lastMessage", message)
        collection.document(id).update("lastTime", Date())
    }

    suspend fun docToChat(chatDoc: ChatDoc, sender: User): Chat {
        val recipientId = if(chatDoc.person1 == sender.id) chatDoc.person2 else chatDoc.person1
        val recipient = userRepository.getUser(recipientId).getOrNull()
        return Chat(
            chatDoc.id,
            sender,
            recipient!!,
            ArrayList(),
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
                chatDocs.clear()
                if (value == null || value.isEmpty) {
                    return@addSnapshotListener
                }
                for (chatDoc in value.documents) {
                    chatDocs.add(chatDoc.toObject(ChatDoc::class.java)!!)
                }
                chatData.value = chatDocs
            }
    }


}