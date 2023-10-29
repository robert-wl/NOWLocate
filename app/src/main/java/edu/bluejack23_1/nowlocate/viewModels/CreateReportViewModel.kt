package edu.bluejack23_1.nowlocate.viewModels

import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import edu.bluejack23_1.nowlocate.helper.ValidationHelper
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import java.util.Date
import java.util.UUID

class CreateReportViewModel : ViewModel() {

    val itemName = MutableLiveData<String>()
    val itemCategory = MutableLiveData<String>()
    val shortDescription = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val lastSeen = MutableLiveData<String>()
    val itemImage = MutableLiveData<Uri>()
    private val errorMessage = MutableLiveData<String>()

    private val reportRepository = ReportRepository()
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

        val report = Report(
            UUID.randomUUID().toString(),
            itemNameString,
            itemImage.value.toString(),
            itemCategoryString,
            shortDescriptionString,
            descriptionString,
            lastSeenString,
            Date()
        )
        reportRepository.addReport(report)

//        db.collection("test")
//            .add(report)
//            .addOnSuccessListener { documentReference ->
//                val documentId = documentReference.id
//                ToastHelper.showMessage(activity, documentId)
//            }
//            .addOnFailureListener { e ->
//                ToastHelper.showMessage(activity, e.message.toString())
//            }

    }

}