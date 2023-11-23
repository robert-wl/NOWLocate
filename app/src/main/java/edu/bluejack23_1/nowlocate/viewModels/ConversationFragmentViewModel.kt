package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.ChatDoc
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConversationFragmentViewModel: ViewModel() {

    var chats: MutableLiveData<ArrayList<Chat>> = MutableLiveData()
    var chatDocs: MutableLiveData<ArrayList<ChatDoc>> = MutableLiveData()

    private val chatRepository = ChatRepository()
    private val authRepository = AuthRepository()
    val isLoading = MutableLiveData(false)

    fun getUserChats(){
        val user = authRepository.getCurrentUser()
        chatRepository.addRealTimeConversationListener(user.id, chatDocs)
    }

    fun updateChatData(data: ArrayList<ChatDoc>){
        isLoading.value = true
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            val temp = ArrayList<Chat>()
            for(chatDoc in data){
                temp.add(chatRepository.docToChat(chatDoc, user))
            }
            chats.value = temp
            withContext(Dispatchers.Main){
                isLoading.value = false
            }
        }
    }

}