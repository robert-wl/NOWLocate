package edu.bluejack23_1.nowlocate.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ChatRepository
import kotlinx.coroutines.launch

class ConversationFragmentViewModel: ViewModel() {
    val chatList: MutableLiveData<ArrayList<Chat>> = MutableLiveData()

    private val chatRepository = ChatRepository()
    private val authRepository = AuthRepository()

    fun getData(){
        viewModelScope.launch {
//            Log.wtf("a", "asdkjhfashfhsjkjfaskhffflsfj")
            val user = authRepository.getCurrentUser()
            chatList.value = chatRepository.getUserChats(user.id)
        }

    }

}