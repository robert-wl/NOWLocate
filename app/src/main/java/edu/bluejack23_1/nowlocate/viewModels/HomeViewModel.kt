package edu.bluejack23_1.nowlocate.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val searchQuery = MutableLiveData<String>()
    val filterQuery = MutableLiveData<String>()
    val reportList = MutableLiveData<ArrayList<Report>>()
    val filterList = MutableLiveData<ArrayList<Filter>>()

    private val reportRepository = ReportRepository()

    init {
        getCategoryData()
    }


    fun getCategoryData(){
        viewModelScope.launch {
            val result = reportRepository.getAllCategory()

            Log.wtf("HomeViewModel", "hahahah" + result.getOrNull()?.size.toString())
            if(result.isSuccess){
                filterList.value = result.getOrNull()
            }
        }
    }

    fun getReportDataFiltered(){
        viewModelScope.launch {
            val result = reportRepository.getReportData(filterQuery.value ?: "")

            if(result.isSuccess){
                reportList.value = result.getOrNull()
            }
        }
    }

    fun getReportDataSearched(){
        viewModelScope.launch {
            val result = reportRepository.getReportDataSearched(searchQuery.value ?: "")
            Log.wtf("HomeSearchedFragment", "hahahah" + result.getOrNull()?.size.toString())
            if(result.isSuccess){
                reportList.value = result.getOrNull()
            }
        }
    }
}