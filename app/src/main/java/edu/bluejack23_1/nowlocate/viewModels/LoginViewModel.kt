package edu.bluejack23_1.nowlocate.viewModels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rememberMe = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun signInHandler(activity: Activity){
        val emailString = email.value ?: ""
        val passwordString = password.value ?: ""
        val rememberMeBool = rememberMe.value ?: false

        if(emailString.isEmpty() || passwordString.isEmpty()){
            errorMessage.value = "All fields must not be empty"
            return
        }

        if(rememberMeBool){
            //remember me logic
        }

        //login logic
    }

}