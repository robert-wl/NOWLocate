package edu.bluejack23_1.nowlocate.viewModels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.StringHelper
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import edu.bluejack23_1.nowlocate.views.activity.ProfileActivity
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class EditProfileViewModel : ViewModel() {
    val firstname = MutableLiveData<String>()
    val lastname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val image = MutableLiveData<Uri>()
    val oldImage = MutableLiveData<Uri>()

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
        image.value = Uri.parse(user.image)
        oldImage.value = Uri.parse(user.image)
    }

    fun handleEditProfile(){
        val firstNameString = firstname.value ?: ""
        val lastNameString = lastname.value ?: ""
        val usernameString = username.value ?: ""
        val emailString = email.value ?: ""
        val genderString = gender.value ?: ""
        val imageUri = image.value ?: Uri.parse("")

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

        if (imageUri.toString() == ""){
            errorMessage.value = StringHelper.getString(R.string.report_image_empty)
            return
        }

        val user = authRepository.getCurrentUser()
        user.firstName = firstNameString
        user.lastName = lastNameString
        user.username = usernameString
        user.gender = genderString
        updateUserData(user)
        authRepository.signIn(user)

        activityToStart.value = ProfileActivity::class
    }

    private fun updateUserData(user: User){
        viewModelScope.launch {

            if(image.value.toString() != oldImage.value.toString()){
                val uri = userRepository.uploadUserImage(image.value)
                user.image = uri ?: ""
            }
            else {
                user.image = oldImage.value.toString()
            }


            authRepository.signIn(user)
            userRepository.updateUserData(user)
        }
    }

}