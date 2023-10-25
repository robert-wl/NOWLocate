package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bluejack23_1.nowlocate.helper.ValidationHelper

class ChangePasswordViewModel : ViewModel() {

    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun handleChangePassword(){
        val passwordString = password.value ?: ""
        val confirmPasswordString = confirmPassword.value ?: ""

        if(passwordString.isEmpty() || confirmPasswordString.isEmpty()){
            errorMessage.value = "All fields must not be empty"
            return
        }

        if(!ValidationHelper.isAlphaNumeric(passwordString)){
            errorMessage.value = "Password must be alphanumeric"
            return
        }

        if(passwordString != confirmPasswordString){
            errorMessage.value = "Confirm password must be the same as password"
            return
        }

        //password must not be the same as old password validation

    }

}