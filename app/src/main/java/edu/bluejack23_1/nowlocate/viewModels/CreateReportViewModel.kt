package edu.bluejack23_1.nowlocate.viewModels

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.StringHelper
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
            errorMessage.value = StringHelper.getString(R.string.empty_field_error_message)
            return
        }

        if(itemCategoryString.isEmpty() || itemCategoryString == "Select Category"){
            errorMessage.value = StringHelper.getString(R.string.report_category_empty)
            return
        }

        if(shortDescriptionString.isEmpty()){
            errorMessage.value = StringHelper.getString(R.string.report_short_description_empty)
            return
        }

        if(descriptionString.isEmpty()){
            errorMessage.value = StringHelper.getString(R.string.report_long_description_empty)
            return
        }

        if(lastSeenString.isEmpty()){
            errorMessage.value = StringHelper.getString(R.string.report_last_seen_empty)
            return
        }

        if (ValidationHelper.numOfWords(itemNameString) < 2){
            errorMessage.value = StringHelper.getString(R.string.report_name_invalid)
            return
        }

        if(!ValidationHelper.hasValidDate(lastSeenString)){
            errorMessage.value = StringHelper.getString(R.string.report_last_seen_invalid)
            return
        }

        if (shortDescriptionString.length > 25){
            errorMessage.value = StringHelper.getString(R.string.report_short_description_invalid)
            return
        }

        if (descriptionString.length > 500){
            errorMessage.value = StringHelper.getString(R.string.report_long_description_invalid)
            return
        }

        if(itemImage.value == null || itemImage.value.toString().isEmpty()){
            errorMessage.value = StringHelper.getString(R.string.report_image_invalid)
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