package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import edu.bluejack23_1.nowlocate.views.activity.ProfileActivity
import kotlin.reflect.KClass

class EditProfileViewModel : ViewModel() {
    val firstname = MutableLiveData<String>()
    val lastname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val gender = MutableLiveData<String>()

    val errorMessage = MutableLiveData<String>()
    val activityToStart = MutableLiveData<KClass<*>>()

    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()

    init {
        val user = authRepository.getCurrentUser()
        firstname.value = user.firstName
        lastname.value = user.lastName
        email.value = user.email
        username.value = user.username
        gender.value = user.gender
    }

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

        var user = authRepository.getCurrentUser()
        user.firstName = firstNameString
        user.lastName = lastNameString
        user.username = usernameString
        user.gender = genderString
        userRepository.updateUserData(user)

        activityToStart.value = ProfileActivity::class

    }

}