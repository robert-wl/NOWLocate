package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bluejack23_1.nowlocate.helper.ValidationHelper

class ForgotPasswordViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun handleResetPassword(){
        val emailString = email.value ?: ""
        if(emailString.isEmpty()){
            errorMessage.value = "Email must not be empty"
            return
        }

        if(!ValidationHelper.emailExists(emailString)){
            errorMessage.value = "Email doesn't exist"
            return
        }

        //handle reset password logic

    }

}