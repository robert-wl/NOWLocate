package edu.bluejack23_1.nowlocate.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat

class ReportDetailViewModel : ViewModel() {

    var report = MutableLiveData<Report>()
    var reportDate = MutableLiveData<String>()
    var reportImage = MutableLiveData<Uri>()
    var name = MutableLiveData<String>()


    private val userRepository = UserRepository()
    init {

    }

    fun handleExtrasData(reportData: Report){
        report.value = reportData
        reportImage.value = Uri.parse(reportData.image)
        reportDate.value = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(reportData.reportDate)

        viewModelScope.launch {
            val result = report.value?.reportedBy?.let {
                userRepository.getUser(it)
            }

            if(result?.isFailure == true){
                return@launch
            }

            val user = result?.getOrNull()

            Log.wtf("USER", user?.firstName + " " + user?.lastName)
            name.value = user?.firstName + " " + user?.lastName
        }
    }

    fun handleDelete(){

    }

}