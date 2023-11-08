package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.helpers.ValidationHelper
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {

    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    private val authRepository = AuthRepository()

    fun handleChangePassword(){
        viewModelScope.launch{
            val passwordString = password.value ?: ""
            val confirmPasswordString = confirmPassword.value ?: ""

            if(passwordString.isEmpty() || confirmPasswordString.isEmpty()){
                errorMessage.value = "All fields must not be empty"
                return@launch
            }

            if(!ValidationHelper.isAlphaNumeric(passwordString)){
                errorMessage.value = "Password must be alphanumeric"
                return@launch
            }

            if(passwordString != confirmPasswordString){
                errorMessage.value = "Confirm password must be the same as password"
                return@launch
            }

            errorMessage.value = authRepository.updateUserPassword(passwordString)
        }

    }

}