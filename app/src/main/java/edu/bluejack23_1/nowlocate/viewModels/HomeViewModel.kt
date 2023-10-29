package edu.bluejack23_1.nowlocate.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel: ViewModel() {
    val searchQuery = MutableLiveData<String>()
    val reportList = MutableLiveData<ArrayList<Report>>(arrayListOf(
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date(), "a"),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date(), "a"),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date(), "a"),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date(), "a"),
    ))
    val filterList = MutableLiveData<ArrayList<Filter>>(arrayListOf(
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
    ))

    private val reportRepository = ReportRepository()

    fun getData(){
        viewModelScope.launch {
            val result = reportRepository.getLatestReport()

            if(result.isSuccess){
                Log.wtf("HomeViewModel",  result.getOrNull().toString())
                reportList.value = result.getOrNull()
            }
        }
    }
}