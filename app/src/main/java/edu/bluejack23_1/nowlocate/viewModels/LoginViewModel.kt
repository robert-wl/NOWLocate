package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.StringHelper
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import edu.bluejack23_1.nowlocate.views.activity.HomeActivity
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class LoginViewModel() : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rememberMe = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val activityToStart = MutableLiveData<KClass<*>>()

    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    init {
        email.value = authRepository.getRememberMeEmailValue()
        password.value = authRepository.getRememberMePasswordValue()
        rememberMe.value =
            (email.value != "" && email.value != null) && (password.value != "" && password.value != null)

        if (rememberMe.value != null && rememberMe.value != false) {
            signInHandler()
        }
    }

    fun signInHandler() {
        val emailString = email.value ?: ""
        val passwordString = password.value ?: ""
        val rememberMeBool = rememberMe.value ?: false

        if (emailString.isEmpty()) {
            errorMessage.value = StringHelper.getString(R.string.email_empty)
            return
        }

        if (passwordString.isEmpty()) {
            errorMessage.value = StringHelper.getString(R.string.password_empty)
            return
        }
        signIn(emailString, passwordString, rememberMeBool);
    }

    private fun signIn(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            val result = authRepository.signIn(email, password)

            if (result.isSuccess) {
                handleRememberMe(email, password, rememberMe)

                val userResult = userRepository.getUser(result.getOrNull()!!.uid)

                if (userResult.isFailure) {
                    errorMessage.value = userResult.exceptionOrNull()?.message ?: StringHelper.getString(R.string.unknown_error)
                    return@launch
                }

                val user = userResult.getOrNull()!!
                authRepository.signIn(user);

                activityToStart.value = HomeActivity::class
                return@launch
            }

            errorMessage.value = result.exceptionOrNull()?.message ?: StringHelper.getString(R.string.unknown_error)
        }
    }

    private fun handleRememberMe(email: String, password: String, rememberMe: Boolean) {
        if (rememberMe) {
            authRepository.setRememberMeValues(email, password)
            return;
        }
        authRepository.removeRememberMeValues()
    }

}