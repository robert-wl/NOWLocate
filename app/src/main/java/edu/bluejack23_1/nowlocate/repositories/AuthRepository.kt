package edu.bluejack23_1.nowlocate.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.bluejack23_1.nowlocate.helpers.SharedPreferencesHelper
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

    fun isSelf(id: String?): Boolean {
        return getCurrentUser().id == id
    }
    fun setRememberMeValues(email: String, password: String){
        SharedPreferencesHelper.setString("emailLogin", email)
        SharedPreferencesHelper.setString("passwordLogin", password)
    }

    fun removeRememberMeValues(){
        SharedPreferencesHelper.setString("emailLogin", "")
        SharedPreferencesHelper.setString("passwordLogin", "")
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
        SharedPreferencesHelper.setString("image", user.image)
    }

    fun getCurrentUser(): User {
        return User(
            SharedPreferencesHelper.getString("id")!!,
            SharedPreferencesHelper.getString("firstname")!!,
            SharedPreferencesHelper.getString("lastname")!!,
            SharedPreferencesHelper.getString("email")!!,
            SharedPreferencesHelper.getString("username")!!,
            SharedPreferencesHelper.getString("image")!!,
            SharedPreferencesHelper.getString("gender")!!
        )
    }

    fun signOut(){
        removeRememberMeValues()
        auth.signOut()
    }
}
