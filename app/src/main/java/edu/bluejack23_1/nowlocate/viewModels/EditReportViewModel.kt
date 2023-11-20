package edu.bluejack23_1.nowlocate.viewModels

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ValidationHelper
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import edu.bluejack23_1.nowlocate.views.activity.HomeActivity
import edu.bluejack23_1.nowlocate.views.activity.ProfileActivity
import kotlinx.coroutines.launch
import java.text.DateFormat
import kotlin.reflect.KClass

class EditReportViewModel : ViewModel() {
    val report = MutableLiveData<Report>()
    val reportName = MutableLiveData<String>()
    val reportCategory = MutableLiveData<String>()
    val reportShortDescription = MutableLiveData<String>()
    val reportLongDescription = MutableLiveData<String>()
    val reportLastSeen = MutableLiveData<String>()
    val reportDate = MutableLiveData<String>()
    val reportImage = MutableLiveData<Uri>()
    var userImage = MutableLiveData<Uri>()
    val name = MutableLiveData<String>()
    val activityToStart = MutableLiveData<KClass<*>>()

    val errorMessage = MutableLiveData<String>()

    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()
    private val reportRepository = ReportRepository()
    fun handleExtrasData(reportData: Report) {
        report.value = reportData
        reportName.value = reportData.name
        reportCategory.value = reportData.category
        reportShortDescription.value = reportData.shortDescription
        reportLongDescription.value = reportData.longDescription
        reportLastSeen.value = reportData.lastSeen
        reportImage.value = Uri.parse(reportData.image)
        reportDate.value =
            DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(reportData.reportDate)

        viewModelScope.launch {
            val result = report.value?.reportedBy?.let {
                userRepository.getUser(it)
            }

            if (result?.isFailure == true) {
                return@launch
            }

            val user = result?.getOrNull()

            name.value = user?.firstName + " " + user?.lastName
            userImage.value = Uri.parse(user?.image)
        }
    }

    fun handleEditReport() {
        val itemNameString = reportName.value ?: ""
        val itemCategoryString = reportCategory.value ?: ""
        val shortDescriptionString = reportShortDescription.value ?: ""
        val descriptionString = reportLongDescription.value ?: ""
        val lastSeenString = reportLastSeen.value ?: ""
        val reportImageString = reportImage.value ?: Uri.parse("")

        if (itemNameString.isEmpty()) {
            errorMessage.value = "Report name must not be empty"
            return
        }

        if (itemCategoryString.isEmpty()) {
            errorMessage.value = "Report category must not be empty"
            return
        }

        if (shortDescriptionString.isEmpty()) {
            errorMessage.value = "Short description must not be empty"
            return
        }

        if (descriptionString.isEmpty()) {
            errorMessage.value = "Description must not be empty"
            return
        }

        if (lastSeenString.isEmpty()) {
            errorMessage.value = "Last seen must not be empty"
            return
        }

        if (reportImageString.toString().isEmpty()) {
            errorMessage.value = "Image must not be empty"
            return
        }

        if (ValidationHelper.numOfWords(itemNameString) < 2) {
            errorMessage.value = "Report name must be at least 2 words"
            return
        }

        if (!ValidationHelper.hasValidDate(lastSeenString)) {
            errorMessage.value = "Last seen must have a substring in a dd-mm-yyyy format"
            return
        }

        if (shortDescriptionString.length > 25) {
            errorMessage.value = "Short description length must not be more than 25 characters"
            return
        }

        if (descriptionString.length > 500) {
            errorMessage.value = "Description length must not be more than 500 characters"
            return
        }

        if (reportImage.value == null || reportImage.value.toString().isEmpty()) {
            errorMessage.value = "Image must be uploaded"
            return
        }


        val newReport = report.value?.copy(
            name = itemNameString,
            category = itemCategoryString,
            shortDescription = shortDescriptionString,
            longDescription = descriptionString,
            lastSeen = lastSeenString,
            image = reportImageString.toString()
        )
        editReport(newReport!!)
    }

    private fun editReport(reportData: Report) {
        viewModelScope.launch {
            if (reportData.image != report.value?.image.toString()) {
                val uri = reportRepository.uploadReportImage(reportData.image.toUri())
                reportData.image = uri ?: ""
            }

            reportRepository.editReport(reportData)

            activityToStart.value = HomeActivity::class
        }
    }

    fun handleMoveToProfile(context: Context) {
        IntentHelper.moveToWithExtra(
            context, ProfileActivity::class.java, "user", authRepository.getCurrentUser()
        )
    }

}