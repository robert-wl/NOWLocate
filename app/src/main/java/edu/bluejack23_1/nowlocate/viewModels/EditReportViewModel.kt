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

class EditReportViewModel: ViewModel() {
    val report = MutableLiveData<Report>()
    val reportName = MutableLiveData<String>()
    val reportCategory = MutableLiveData<String>()
    val reportShortDescription = MutableLiveData<String>()
    val reportLongDescription = MutableLiveData<String>()
    val reportLastSeen = MutableLiveData<String>()
    val reportDate = MutableLiveData<String>()
    val reportImage = MutableLiveData<Uri>()
    val name = MutableLiveData<String>()

    private val userRepository = UserRepository()
    fun handleExtrasData(reportData: Report){
        Log.wtf("report", reportData.toString())
        report.value = reportData
        reportName.value = reportData.name
        reportCategory.value = reportData.category
        reportShortDescription.value = reportData.shortDescription
        reportLongDescription.value = reportData.longDescription
        reportLastSeen.value = reportData.lastSeen
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

            name.value = user?.firstName + " " + user?.lastName
        }
    }
}