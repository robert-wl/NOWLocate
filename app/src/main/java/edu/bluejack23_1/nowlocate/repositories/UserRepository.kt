package edu.bluejack23_1.nowlocate.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.nowlocate.models.User
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()

    fun addUser(user: User){
        db.collection("users").document(user.id).set(user)
    }

    suspend fun getUser(id: String): Result<User?> {
        val documentReference = db.collection("users").document(id)

        return try {
            val documentSnapshot = documentReference.get().await()

            if(documentSnapshot.exists()){
                val user = documentSnapshot.toObject(User::class.java)
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception){
            Log.wtf("a", e)
            Result.failure(e)
        }

    }
}