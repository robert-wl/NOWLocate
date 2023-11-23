package edu.bluejack23_1.nowlocate.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel: ViewModel() {
    val reportList = MutableLiveData<ArrayList<Report>>()
    val isLoading = MutableLiveData(false)
    val isAscending = MutableLiveData(false)
    var page = 1

    private var limit = 5
    private val reportRepository = ReportRepository()

    fun getData(){
        isLoading.value = true

        viewModelScope.launch {
            val result = reportRepository.getLatestReport(page * limit, isAscending.value!!)

            if(result.isSuccess){
                reportList.value = result.getOrNull()
            }
            withContext(Dispatchers.Main) {
                if (isActive) {
                    isLoading.value = false
                }
            }
        }
    }
}