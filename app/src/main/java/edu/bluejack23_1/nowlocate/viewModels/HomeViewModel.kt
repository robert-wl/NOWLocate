package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.models.Report
import java.util.Date

class HomeViewModel: ViewModel() {
    val searchQuery = MutableLiveData<String>()
    val reportList = MutableLiveData<ArrayList<Report>>(arrayListOf(
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date()),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date()),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date()),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date()),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date()),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date()),
        Report("1", "rawr", "Bicycle", "Transportation", "A bicycle was stolen in front of the mall", "A bicycle was stolen in front of the mall", "a", Date()),
    ))
    val filterList = MutableLiveData<ArrayList<Filter>>(arrayListOf(
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
        Filter("Filter 1", 2),
    ))
}