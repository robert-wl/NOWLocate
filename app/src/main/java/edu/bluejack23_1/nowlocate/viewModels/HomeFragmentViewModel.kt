package edu.bluejack23_1.nowlocate.viewModels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import kotlinx.coroutines.launch

class HomeFragmentViewModel: ViewModel() {
    val reportList = MutableLiveData<ArrayList<Report>>()
    val isLoading = MutableLiveData<Boolean>(false)
    var page = 1
    private var limit = 10
    private val reportRepository = ReportRepository()

    fun getData(){
        isLoading.value = true
        val start = (page - 1) * limit
        val end = page * limit


        viewModelScope.launch {
            val result = reportRepository.getLatestReport(start, end)

            Log.wtf("HomeViewModel",  result.toString())
            Log.wtf("HomeViewModel",  "" + start + " " + end)
            if(result.isSuccess){
                Log.wtf("HomeViewModel",  result.getOrNull()?.size.toString())
                reportList.value = result.getOrNull()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            isLoading.value = false
        }, 2000)
    }
}