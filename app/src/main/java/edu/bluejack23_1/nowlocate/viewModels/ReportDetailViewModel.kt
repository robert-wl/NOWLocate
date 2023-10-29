package edu.bluejack23_1.nowlocate.viewModels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import kotlinx.coroutines.launch

class ReportDetailViewModel : ViewModel() {

    var report = MutableLiveData<Report>()
    var reportDate = MutableLiveData<String>()
    var reportImage = MutableLiveData<Uri>()


    private val userRepository = UserRepository()
    init {

    }

    fun handleExtrasData(reportData: Report){
        report.value = reportData
        reportImage.value = Uri.parse(reportData.image)
        reportDate.value = reportData.reportDate.toString()

        viewModelScope.launch {
            val user = report.value?.id?.let {
                userRepository.getUser(it)
            }

        }
    }

    fun handleDelete(){

    }

}