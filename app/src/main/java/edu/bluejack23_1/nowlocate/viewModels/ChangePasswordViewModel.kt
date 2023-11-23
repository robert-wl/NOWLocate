package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.helpers.ValidationHelper
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {

    val oldPassword = MutableLiveData("")
    val newPassword = MutableLiveData("")
    val confirmPassword = MutableLiveData("")
    val errorMessage = MutableLiveData<String>()

    private val authRepository = AuthRepository()

    fun handleChangePassword(){
        viewModelScope.launch{
            val oldPasswordString = oldPassword.value ?: ""
            val newPasswordString = newPassword.value ?: ""
            val confirmPasswordString = confirmPassword.value ?: ""

            if(oldPasswordString.isEmpty() || newPasswordString.isEmpty() || confirmPasswordString.isEmpty()){
                errorMessage.value = "All fields must not be empty"
                return@launch
            }

            if(!ValidationHelper.isAlphaNumeric(newPasswordString)){
                errorMessage.value = "Password must be alphanumeric"
                return@launch
            }

            if(newPasswordString != confirmPasswordString){
                errorMessage.value = "Confirm password must be the same as password"
                return@launch
            }

            errorMessage.value = authRepository.updateUserPassword(oldPasswordString, newPasswordString)
        }

    }

}