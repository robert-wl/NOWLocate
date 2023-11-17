package edu.bluejack23_1.nowlocate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bluejack23_1.nowlocate.models.Message
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ChatRepository
import java.util.Date

class ChatViewModel : ViewModel() {

    var message: MutableLiveData<String> = MutableLiveData()
    var chatId: MutableLiveData<String> = MutableLiveData()

    private val authRepository = AuthRepository()
    private val chatRepository = ChatRepository()

    fun sendMessage(){
        val messageString = message.value ?: return
        if(messageString.isEmpty()){
            return
        }
        val user = authRepository.getCurrentUser()
        chatRepository.sendMessage(chatId.value!!, Message(user.id, messageString, Date()))
    }

}