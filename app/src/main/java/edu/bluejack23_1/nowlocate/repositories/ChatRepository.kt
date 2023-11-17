package edu.bluejack23_1.nowlocate.repositories

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.ChatDoc
import edu.bluejack23_1.nowlocate.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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

        return docToChat(chatQuery.documents[0].toObject(ChatDoc::class.java)!!)
    }

    suspend fun addChat(id1: String, id2: String): Chat {
        val result = chatExists(id1, id2)

        if (result != null) {
            return result
        }

        val chatDoc = ChatDoc(UUID.randomUUID().toString(), id1, id2, ArrayList(), "", Date())
        collection.document(chatDoc.id).set(chatDoc).await()
        return docToChat(chatDoc)
    }

    suspend fun getUserChats(id: String): ArrayList<Chat> {
        val chatQuery = collection
            .where(
                Filter.or(
                    Filter.equalTo("person1", id),
                    Filter.equalTo("person2", id)
                )
            ).get().await()
        val chats: ArrayList<Chat> = ArrayList()
        if (chatQuery.isEmpty) {
            return chats
        }
        for (chatDoc in chatQuery.documents) {
            chats.add(docToChat(chatDoc.toObject(ChatDoc::class.java)!!))
        }
        chats.sortByDescending { chat -> chat.lastTime }
        return chats
    }

    fun sendMessage(id: String, message: Message) {
        collection.document(id).collection("messages").add(message)
        collection.document(id).update("lastMessage", message)
        collection.document(id).update("lastTime", Date())
    }

    private suspend fun docToChat(chatDoc: ChatDoc): Chat {
        val sender = authRepository.getCurrentUser()
        val recipientId = if (chatDoc.person1 == sender.id) chatDoc.person2 else chatDoc.person1
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

    suspend fun addConversationListener(id: String): ArrayList<Chat>{
        val chatDocs: ArrayList<ChatDoc> = ArrayList()
        val chats: ArrayList<Chat> = ArrayList()
        collection.where(
            Filter.or(
                Filter.equalTo("person1", id),
                Filter.equalTo("person2", id)
            )
        ).addSnapshotListener{snapshot, e ->
            if(snapshot != null && !snapshot.isEmpty){
                for (chatDoc in snapshot.documents) {
                    chatDocs.add(chatDoc.toObject(ChatDoc::class.java)!!)
                }
            }
        }
        for (chatDoc in chatDocs){
            chats.add(docToChat(chatDoc))
        }
        chats.sortByDescending { chat -> chat.lastTime }
        return chats
    }

    suspend fun addRealTimeConversationListener(id: String, callback: (ArrayList<Chat>) -> Unit) {
        // Use coroutineScope to handle the asynchronous nature of addSnapshotListener
        collection
            .where(
                Filter.or(
                    Filter.equalTo("person1", id),
                    Filter.equalTo("person2", id)
                )
            )
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null && !snapshot.isEmpty) {
                    // Use launch to create a coroutine within the callback
                    CoroutineScope(Dispatchers.Default).launch {
                        val chatDocs: ArrayList<ChatDoc> = ArrayList()

                        for (chatDoc in snapshot.documents) {
                            chatDocs.add(chatDoc.toObject(ChatDoc::class.java)!!)
                        }

                        // Process the data using suspend functions within coroutineScope
                        val chats: ArrayList<Chat> = ArrayList()
                        for (chatDoc in chatDocs) {
                            try {
                                val chat = withContext(Dispatchers.Default) {
                                    docToChat(chatDoc)
                                }
                                chats.add(chat)
                            } catch (conversionException: Exception) {
                                // Handle errors during conversion for a specific document
                                // Log or handle the error for a specific document
                            }
                        }

                        // Invoke the callback with the processed data
                        callback(chats)
                    }
                } else {
                    // Handle errors, if any
                    e?.let {
                        // Log or handle the error
                        callback(ArrayList()) // or callback(emptyList()) depending on your error handling strategy
                    }
                }
            }
    }

}