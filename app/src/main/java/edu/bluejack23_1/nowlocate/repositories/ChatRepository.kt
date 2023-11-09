package edu.bluejack23_1.nowlocate.repositories

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.Message
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("chats")

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
            ).get().await()

        if(!chatQuery.isEmpty){
            return null
        }

        return chatQuery.documents[0].toObject(Chat::class.java)
    }

    suspend fun addChat(id1: String, id2: String): Chat{
        val result = chatExists(id1, id2)

        if(result != null){
            return result
        }

        val chat = Chat(UUID.randomUUID().toString(), id1, id2)
        collection.document(chat.id).set(chat).await()
        return chat
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
        if(chatQuery.isEmpty){
            return chats
        }
        for(chat in chatQuery.documents){
            val temp = chat.toObject(Chat::class.java)
            chats.add(temp!!)
        }
        return chats
    }

    fun sendMessage(id: String, message: Message) {
        collection.document(id).collection("messages").add(message)
    }

}