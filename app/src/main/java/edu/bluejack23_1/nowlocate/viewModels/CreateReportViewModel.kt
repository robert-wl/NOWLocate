package edu.bluejack23_1.nowlocate.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import edu.bluejack23_1.nowlocate.helper.ToastHelper
import edu.bluejack23_1.nowlocate.helper.ValidationHelper
import edu.bluejack23_1.nowlocate.models.Report

class CreateReportViewModel : ViewModel() {

    val itemName = MutableLiveData<String>()
    val itemCategory = MutableLiveData<String>()
    val shortDescription = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val lastSeen = MutableLiveData<String>()
    val itemImage = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun handleCreateReport(activity: AppCompatActivity){
        val itemNameString = itemName.value ?: ""
        val itemCategoryString = itemCategory.value ?: ""
        val shortDescriptionString = shortDescription.value ?: ""
        val descriptionString = description.value ?: ""
        val lastSeenString = lastSeen.value ?: ""

        if (itemNameString.isEmpty() || itemCategoryString.isEmpty() || shortDescriptionString.isEmpty() ||
            descriptionString.isEmpty() || lastSeenString.isEmpty()) {
            errorMessage.value = "All fields must not be empty"
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

        val db = Firebase.firestore
        val report = Report(itemNameString, itemCategoryString, shortDescriptionString, descriptionString, lastSeenString)

        db.collection("test")
            .add(report)
            .addOnSuccessListener { documentReference ->
                val documentId = documentReference.id
                ToastHelper.showMessage(activity, documentId)
            }
            .addOnFailureListener { e ->
                ToastHelper.showMessage(activity, e.message.toString())
            }

    }

}