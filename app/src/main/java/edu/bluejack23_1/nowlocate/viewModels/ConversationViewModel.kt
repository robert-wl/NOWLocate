package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ChatRepository
import kotlinx.coroutines.launch

class ConversationViewModel: ViewModel() {

    var chats: MutableLiveData<ArrayList<Chat>> = MutableLiveData()

    private val chatRepository = ChatRepository()
    private val authRepository = AuthRepository()

    init {
        getUserChats()
    }

    private fun getUserChats(){
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            chatRepository.addRealTimeConversationListener(user.id) { updatedChats ->
                chats.value = updatedChats
            }
        }
    }


}