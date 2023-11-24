package edu.bluejack23_1.nowlocate.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.StringHelper
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()

    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun handleResetPassword(activity: AppCompatActivity){
        viewModelScope.launch {
            val emailString = email.value ?: ""
            if(emailString.isEmpty()){
                errorMessage.value = StringHelper.getString(R.string.email_empty)
                return@launch
            }
            val user = userRepository.getUserByEmail(emailString)

            if(user.isFailure){
                errorMessage.value = StringHelper.getString(R.string.email_not_found)
                return@launch
            }
            val result = authRepository.sendResetPasswordEmail(emailString)

            if(result.isSuccess){
                errorMessage.value = StringHelper.getString(R.string.reset_password_email_sent)
                return@launch
            }
            val errorMsg = result.exceptionOrNull()?.message
            errorMessage.value = errorMsg ?: StringHelper.getString(R.string.unknown_error)

        }
    }

}