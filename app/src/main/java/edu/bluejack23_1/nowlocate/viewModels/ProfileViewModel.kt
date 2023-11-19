package edu.bluejack23_1.nowlocate.viewModels


import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import edu.bluejack23_1.nowlocate.views.activity.LoginActivity
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ProfileViewModel: ViewModel() {
    private val user = MutableLiveData<User>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val image = MutableLiveData<Uri>()
    val activityToStart = MutableLiveData<KClass<*>>()
    val reportList = MutableLiveData<ArrayList<Report>>()
    val isLoading = MutableLiveData<Boolean>(false)
    val isAscending = MutableLiveData<Boolean>(false)
    var page = 1


    private var limit = 5
    private val reportRepository = ReportRepository()
    private val authRepository = AuthRepository()


    fun getData(){
        isLoading.value = true

        viewModelScope.launch {
            val result = reportRepository.getLatestReport(page * limit, isAscending.value!!)

            if(result.isSuccess){
                Log.wtf("HomeViewModel",  result.getOrNull()?.size.toString())
                reportList.value = result.getOrNull()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            isLoading.value = false
        }, 2000)
    }

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
