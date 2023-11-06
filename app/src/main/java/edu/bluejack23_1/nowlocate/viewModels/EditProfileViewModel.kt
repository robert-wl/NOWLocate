package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditProfileViewModel : ViewModel() {
    val firstname = MutableLiveData<String>()
    val lastname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun handleEditProfile(){
        val firstNameString = firstname.value ?: ""
        val lastNameString = lastname.value ?: ""
        val usernameString = username.value ?: ""
        val emailString = email.value ?: ""
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


    }

}