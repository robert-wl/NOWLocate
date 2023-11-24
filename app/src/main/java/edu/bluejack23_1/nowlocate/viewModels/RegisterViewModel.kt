package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.StringHelper
import edu.bluejack23_1.nowlocate.helpers.ValidationHelper
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import edu.bluejack23_1.nowlocate.views.activity.LoginActivity
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
            errorMessage.value = StringHelper.getString(R.string.first_name_empty)
            return
        }

        if (lastNameString.isEmpty()){
            errorMessage.value = StringHelper.getString(R.string.last_name_empty)
            return
        }

        if (usernameString.isEmpty()){
            errorMessage.value = StringHelper.getString(R.string.username_empty)
            return
        }

        if (emailString.isEmpty()){
            errorMessage.value = StringHelper.getString(R.string.email_empty)
            return
        }

        if(genderString == "-"){
            errorMessage.value = StringHelper.getString(R.string.gender_empty)
            return
        }

        if(!ValidationHelper.isAlphaNumeric(passwordString)){
            errorMessage.value = StringHelper.getString(R.string.alphanumeric_error_message)
            return
        }

        if(passwordString != confirmPasswordString){
            errorMessage.value = StringHelper.getString(R.string.password_not_match_error_message)
            return
        }

        signUp(firstNameString, lastNameString, emailString, usernameString, passwordString, genderString);
    }

    private fun signUp(firstName: String, lastName: String, email: String, userName: String, password: String, gender: String) {
        viewModelScope.launch {
            val result = authRepository.createAuthAccount(email, password)

            if(result.isFailure){
                errorMessage.value = result.exceptionOrNull()?.message ?: StringHelper.getString(R.string.unknown_error)
                return@launch
            }
            val user = result.getOrNull() ?: return@launch

            userRepository.addUser(User(user.uid, firstName, lastName, email, userName, "", gender))

            activityToStart.value = LoginActivity::class
        }
    }

}