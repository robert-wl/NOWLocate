package edu.bluejack23_1.nowlocate.viewModels

import android.app.Activity
import android.app.Application
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.helper.SharedPreferences
import edu.bluejack23_1.nowlocate.repository.AuthRepository
import edu.bluejack23_1.nowlocate.views.MainActivity
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rememberMe = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val activityToStart = MutableLiveData<KClass<*>>()

    private val authRepository = AuthRepository()
    private val sharedPreferences = SharedPreferences(application)

    init {
        sharedPreferences.getString("email")?.let {
            email.value = it
        }
        sharedPreferences.getString("password")?.let {
            password.value = it
        }
    }

    fun signInHandler(){
        val emailString = email.value ?: ""
        val passwordString = password.value ?: ""
        val rememberMeBool = rememberMe.value ?: false

        if(emailString.isEmpty()){
            errorMessage.value = "Email must be filled"
            return
        }

        if(passwordString.isEmpty()){
            errorMessage.value = "Password must be filled"
            return
        }
        signIn(emailString, passwordString, rememberMeBool);
    }

    fun signIn(email: String, password: String, rememberMe: Boolean){
        viewModelScope.launch {
            val result = authRepository.signIn(email, password)

            if(result.isSuccess){
                if(rememberMe){
                    sharedPreferences.setString("email", email)
                    sharedPreferences.setString("password", password)
                }

                activityToStart.value = MainActivity::class
                return@launch
            }
            errorMessage.value = result.exceptionOrNull()?.message ?: "Unknown error"
        }
    }

}