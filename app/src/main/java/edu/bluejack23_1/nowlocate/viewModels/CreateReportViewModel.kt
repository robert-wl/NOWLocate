package edu.bluejack23_1.nowlocate.viewModels

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.helpers.ValidationHelper
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import edu.bluejack23_1.nowlocate.views.activity.ReportDetailActivity
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import kotlin.reflect.KClass

class CreateReportViewModel : ViewModel() {

    val itemName = MutableLiveData<String>()
    val itemCategory = MutableLiveData<String>()
    val shortDescription = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val lastSeen = MutableLiveData<String>()
    val itemImage = MutableLiveData<Uri>()
    val activityToStart = MutableLiveData<KClass<*>>()
    val extrasParcel = MutableLiveData<Parcelable>()
    private val errorMessage = MutableLiveData<String>()

    private val reportRepository = ReportRepository()
    private val authRepository = AuthRepository()
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun handleCreateReport(){
        val itemNameString = itemName.value ?: ""
        val itemCategoryString = itemCategory.value ?: ""
        val shortDescriptionString = shortDescription.value ?: ""
        val descriptionString = description.value ?: ""
        val lastSeenString = lastSeen.value ?: ""

        if(itemNameString.isEmpty()){
            errorMessage.value = "Report name must not be empty"
            return
        }

        if(itemCategoryString.isEmpty()){
            errorMessage.value = "Report category must not be empty"
            return
        }

        if(shortDescriptionString.isEmpty()){
            errorMessage.value = "Short description must not be empty"
            return
        }

        if(descriptionString.isEmpty()){
            errorMessage.value = "Description must not be empty"
            return
        }

        if(lastSeenString.isEmpty()){
            errorMessage.value = "Last seen must not be empty"
            return
        }

        if (ValidationHelper.numOfWords(itemNameString) < 1){
            errorMessage.value = "Report name must be at least 1 word"
            return
        }

        if (shortDescriptionString.length > 25){
            errorMessage.value = "Short description length must not be more than 25 characters"
            return
        }

        if (descriptionString.length > 500){
            errorMessage.value = "Description length must not be more than 500 characters"
            return
        }

        if(itemImage.value == null || itemImage.value.toString().isEmpty()){
            errorMessage.value = "Image must be uploaded"
            return
        }

        val user = authRepository.getCurrentUser()

        val report = Report(
            UUID.randomUUID().toString(),
            itemNameString,
            itemImage.value.toString(),
            itemCategoryString,
            shortDescriptionString,
            descriptionString,
            lastSeenString,
            Date(),
            user.id
        )

        addReport(report)
    }

    private fun addReport(report: Report){
        viewModelScope.launch {
            val uri = reportRepository.uploadReportImage(report.image.toUri())
            report.image = uri ?: ""

            reportRepository.addReport(report)

            extrasParcel.value = report
            activityToStart.value = ReportDetailActivity::class
        }
    }
}