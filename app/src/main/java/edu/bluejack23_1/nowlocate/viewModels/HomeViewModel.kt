package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val searchQuery = MutableLiveData<String>()
    val filterQuery = MutableLiveData<String>()
    val reportList = MutableLiveData<ArrayList<Report>>()
    val filterList = MutableLiveData<ArrayList<Filter>>()
    val isAscending = MutableLiveData<Boolean>(false)

    private val reportRepository = ReportRepository()

    init {
        getCategoryData()
    }


    fun getCategoryData() {
        viewModelScope.launch {
            val result = reportRepository.getAllCategory()

            if (result.isSuccess) {
                filterList.value = result.getOrNull()
            }
        }
    }

    fun getReportDataFiltered() {
        if (filterQuery.value == null) {
            return
        }

        viewModelScope.launch {
            val result = reportRepository.getReportData(filterQuery.value ?: "")

            if (result.isSuccess) {
                reportList.value = result.getOrNull()
            }
        }
    }

    fun getReportDataSearched() {
        if (searchQuery.value == null) {
            return
        }

        viewModelScope.launch {
            val result = reportRepository.getReportDataSearched(searchQuery.value ?: "")
            if (result.isSuccess) {
                reportList.value = result.getOrNull()
            }
        }
    }
}