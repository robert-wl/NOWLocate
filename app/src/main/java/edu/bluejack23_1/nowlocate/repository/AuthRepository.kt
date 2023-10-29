package edu.bluejack23_1.nowlocate.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    suspend fun createAuthAccount(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            Result.success(result.user)
        } catch (e: Exception){

            Result.failure(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()

            Result.success(result.user)
        } catch (e: Exception){

            Result.failure(e)
        }
    }
}