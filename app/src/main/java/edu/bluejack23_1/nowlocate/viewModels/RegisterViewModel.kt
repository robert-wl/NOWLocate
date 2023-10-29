package edu.bluejack23_1.nowlocate.viewModels

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.helper.ValidationHelper
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.repository.AuthRepository
import edu.bluejack23_1.nowlocate.repository.UserRepository
import edu.bluejack23_1.nowlocate.views.LoginActivity
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class RegisterViewModel : ViewModel(){
    val firstname = MutableLiveData<String>()
    val lastname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val activityToStart = MutableLiveData<KClass<*>>()


    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()


    fun signUpHandler(){
        val firstNameString = firstname.value ?: ""
        val lastNameString = lastname.value ?: ""
        val emailString = email.value ?: ""
        val usernameString = username.value ?: ""
        val passwordString = password.value ?: ""
        val confirmPasswordString = confirmPassword.value ?: ""
        val genderString = gender.value ?: ""

        if (firstNameString.isEmpty()){
            errorMessage.value = "First name must not be empty"
            return
        }

        if (lastNameString.isEmpty()){
            errorMessage.value = "Last name must not be empty"
            return
        }

        if (usernameString.isEmpty()){
            errorMessage.value = "Username must not be empty"
            return
        }

        if (emailString.isEmpty()){
            errorMessage.value = "Email must not be empty"
            return
        }

        if(genderString == "-"){
            errorMessage.value = "Gender must be selected"
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

        signUp(firstNameString, lastNameString, emailString, usernameString, passwordString, genderString);
    }

    fun signUp(firstName: String, lastName: String, email: String, userName: String, password: String, gender: String) {
        viewModelScope.launch {
            val result = authRepository.createAuthAccount(email, password)

            if(result.isFailure){
                errorMessage.value = result.exceptionOrNull()?.message ?: "Unknown error"
                return@launch
            }
            val user = result.getOrNull() ?: return@launch

            userRepository.addUser(User(user.uid, firstName, lastName, email, userName, gender))

            activityToStart.value = LoginActivity::class
        }
    }

}