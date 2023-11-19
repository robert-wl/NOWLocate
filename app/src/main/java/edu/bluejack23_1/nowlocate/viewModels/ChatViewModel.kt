package edu.bluejack23_1.nowlocate.viewModels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.Message
import edu.bluejack23_1.nowlocate.models.MessageDoc
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ChatRepository
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class ChatViewModel : ViewModel() {

    var messages: MutableLiveData<ArrayList<Message>> = MutableLiveData()
    var messageDocs: MutableLiveData<ArrayList<MessageDoc>> = MutableLiveData()
    var message: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var profileImage: MutableLiveData<Uri> = MutableLiveData()
    var chat: MutableLiveData<Chat> = MutableLiveData()

    private val authRepository = AuthRepository()
    private val chatRepository = ChatRepository()

    fun handleExtrasData(chatData: Chat) {
        chat.value = chatData
        name.value = "${chatData.recipient.firstName} ${chatData.recipient.lastName}"
        profileImage.value = Uri.parse(chatData.recipient.image)

    }

    fun getUserMessages(){
        chatRepository.addRealTimeMessageListener(chat.value!!.id, messageDocs)
    }

    fun updateMessageData(data: ArrayList<MessageDoc>){
        val temp = ArrayList<Message>()
        val curr = chat.value!!
        for(messageDoc in data){
            val sender = if(messageDoc.sender == curr.sender.id) curr.sender else curr.recipient
            temp.add(Message(sender,messageDoc.message, messageDoc.sentAt))
        }
        messages.value = temp
    }

    fun sendMessage(){
        val messageString = message.value ?: return
        if(messageString.isEmpty()){
            return
        }
        val user = authRepository.getCurrentUser()
        chatRepository.sendMessage(chat.value!!.id, MessageDoc(UUID.randomUUID().toString(), user.id, messageString, Date()))
        message.value = ""
    }

}