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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

class ProfileViewModel: ViewModel() {
    private val user = MutableLiveData<User>()
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
            val result = reportRepository.getUserLatestReport(isAscending.value!!)

            if(result.isSuccess){
                val temp: ArrayList<Report> = ArrayList()
                for (report in result.getOrNull()!!){
                    if(report.reportedBy == user.value!!.id){
                        temp.add(report)
                    }
                    if(temp.size >= limit * page){
                        break
                    }
                }
                reportList.value = temp
            }
            withContext(Dispatchers.Main) {
                if (isActive) {
                    isLoading.value = false
                }
            }
        }
    }

    fun handleExtrasData(userExtras: User?) {
        if (userExtras == null) {
            val userData = authRepository.getCurrentUser()
            user.value = userData
            username.value = userData.username
            email.value = userData.email
            image.value = Uri.parse(userData.image)
        } else {
            user.value = userExtras
            username.value = userExtras.username
            email.value = userExtras.email
            image.value = Uri.parse(userExtras.image)
        }
        getData()
    }

    fun handleLogout() {
        authRepository.signOut()
        activityToStart.value = LoginActivity::class
    }
}
