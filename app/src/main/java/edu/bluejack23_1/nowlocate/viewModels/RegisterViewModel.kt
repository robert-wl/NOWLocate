package edu.bluejack23_1.nowlocate.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bluejack23_1.nowlocate.helper.ValidationHelper

class RegisterViewModel : ViewModel(){
    val firstname = MutableLiveData<String>()
    val lastname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun signUpHandler(){
        val firstNameString = firstname.value ?: ""
        val lastNameString = lastname.value ?: ""
        val emailString = email.value ?: ""
        val usernameString = username.value ?: ""
        val passwordString = password.value ?: ""
        val confirmPasswordString = confirmPassword.value ?: ""
        val genderString = gender.value ?: ""

        if (firstNameString.isEmpty() || lastNameString.isEmpty() || emailString.isEmpty() || usernameString.isEmpty() ||
            passwordString.isEmpty() || confirmPasswordString.isEmpty() || genderString.isEmpty()) {
            errorMessage.value = "All fields must not  be empty"
            return
        }

        if (ValidationHelper.emailExists(emailString)){
            errorMessage.value = "Email is already taken"
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

        //create account logic

    }

}