package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.StringHelper
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
                errorMessage.value = StringHelper.getString(R.string.empty_field_error_message)
                return@launch
            }

            if(!ValidationHelper.isAlphaNumeric(newPasswordString)){
                errorMessage.value = StringHelper.getString(R.string.alphanumeric_error_message)
                return@launch
            }

            if(newPasswordString != confirmPasswordString){
                errorMessage.value = StringHelper.getString(R.string.password_not_match_error_message)
                return@launch
            }

            errorMessage.value = authRepository.updateUserPassword(oldPasswordString, newPasswordString)
        }

    }

}