package edu.bluejack23_1.nowlocate.viewModels


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.views.activity.LoginActivity
import kotlin.reflect.KClass

class ProfileViewModel: ViewModel() {
    private val user = MutableLiveData<User>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val image = MutableLiveData<Uri>()
    val activityToStart = MutableLiveData<KClass<*>>()


    private val authRepository = AuthRepository()
    fun handleExtrasData(userExtras: User?){
        if(userExtras == null) {
            val userData = authRepository.getCurrentUser()
            user.value = userData
            firstName.value = userData.firstName
            lastName.value = userData.lastName
            username.value = userData.username
            email.value = userData.email
            image.value = Uri.parse(userData.image)
        }
    }

    fun handleLogout(){
        authRepository.signOut()
        activityToStart.value = LoginActivity::class
    }
}
