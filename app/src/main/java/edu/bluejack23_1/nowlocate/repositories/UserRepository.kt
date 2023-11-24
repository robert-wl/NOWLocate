package edu.bluejack23_1.nowlocate.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import edu.bluejack23_1.nowlocate.models.User
import kotlinx.coroutines.tasks.await
import java.util.UUID

class       UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storageDB = FirebaseStorage.getInstance()

    fun addUser(user: User) {
        db.collection("users").document(user.id).set(user)
    }

    suspend fun getUser(id: String): Result<User?> {

        val documentReference = db.collection("users").document(id)

        return try {
            val documentSnapshot = documentReference.get().await()

            if (documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(User::class.java)
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Log.wtf("a", e)
            Result.failure(e)
        }

    }

    suspend fun uploadUserImage(uri: Uri?): String? {
        if (uri == null || uri == Uri.EMPTY) {
            return null
        }

        val fileName = UUID.randomUUID().toString() + ".jpg";

        val taskSnapshot = storageDB.reference.child("images/$fileName").putFile(uri).await()

        return taskSnapshot.storage.downloadUrl.await().toString()
    }

    suspend fun getUserByEmail(email: String): Result<User?> {

        val documentReference = db.collection("users")

        return try {

            val querySnapshot = documentReference.whereEqualTo("email", email).get().await()

            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                if (documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    Result.success(user)
                } else {
                    Result.failure(java.lang.Exception("Document is empty"))
                }
            } else {
                Result.failure(java.lang.Exception("Email not found"))
            }

        } catch (e: java.lang.Exception) {
            Log.wtf("a", e)
            Result.failure(e)
        }

    }

    fun updateUserData(user: User) {
        db.collection("users").document(user.id).set(user)
    }

}