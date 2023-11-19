package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class ConversationViewModel: ViewModel() {

    var searchQuery: MutableLiveData<String> = MutableLiveData()

}