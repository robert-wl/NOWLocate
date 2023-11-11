package edu.bluejack23_1.nowlocate.repositories

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.ChatDoc
import edu.bluejack23_1.nowlocate.models.Message
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

}