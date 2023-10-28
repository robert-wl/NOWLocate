package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportDetailViewModel : ViewModel() {

    val reportImage = MutableLiveData<String>()
    val reportName = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val shortDescription = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val lastSeen = MutableLiveData<String>()

    val reportedBy = MutableLiveData<String>()
    val reportedAt = MutableLiveData<String>()
    val reporterImage = MutableLiveData<String>()

    init {
        reportName.value = "test"
        category.value = "test"
        shortDescription.value = "test"
        description.value = "test"
        lastSeen.value = "test"
        reportedBy.value = "test"
        reportedAt.value = "test"
    }

    fun handleDelete(){

    }

}