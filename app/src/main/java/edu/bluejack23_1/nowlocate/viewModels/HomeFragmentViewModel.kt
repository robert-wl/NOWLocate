package edu.bluejack23_1.nowlocate.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import kotlinx.coroutines.launch

class HomeFragmentViewModel: ViewModel() {
    val reportList = MutableLiveData<ArrayList<Report>>()
    val isLoading = MutableLiveData<Boolean>(false)
    val isAscending = MutableLiveData<Boolean>(false)
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
        }

        Handler(Looper.getMainLooper()).postDelayed({
            isLoading.value = false
        }, 2000)
    }
}