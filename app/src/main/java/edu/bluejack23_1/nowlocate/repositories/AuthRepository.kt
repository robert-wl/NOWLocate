package edu.bluejack23_1.nowlocate.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.bluejack23_1.nowlocate.helper.SharedPreferencesHelper
import edu.bluejack23_1.nowlocate.models.User
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

    fun getRememberMeEmailValue(): String? {
        return SharedPreferencesHelper.getString("emailLogin")
    }

    fun getRememberMePasswordValue(): String? {
        return SharedPreferencesHelper.getString("passwordLogin")
    }

    fun signIn(user: User){
        SharedPreferencesHelper.setString("id", user.id)
        SharedPreferencesHelper.setString("username", user.username)
        SharedPreferencesHelper.setString("email", user.email)
        SharedPreferencesHelper.setString("firstname", user.firstName)
        SharedPreferencesHelper.setString("lastname", user.lastName)
        SharedPreferencesHelper.setString("gender", user.gender)
    }

    fun signOut(){
        auth.signOut()
    }
}
