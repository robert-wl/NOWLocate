package edu.bluejack23_1.nowlocate.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.adapters.ChatAdapter
import edu.bluejack23_1.nowlocate.databinding.ActivityChatBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.SystemHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.viewModels.ChatViewModel

class ChatActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var sendBtn: ImageButton
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatRV: RecyclerView
    private lateinit var backBtn: ImageButton
    private lateinit var messagesSV: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityChatBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        binding.viewModel = viewModel

        val chatExtra = intent.getParcelableExtra("chat", Chat::class.java)

        if(chatExtra == null){
            IntentHelper.moveBack(this)
            return
        }

        viewModel.handleExtrasData(chatExtra)
        viewModel.getUserMessages()
    }

    override fun elementHandler() {
        sendBtn = binding.btnSend
        backBtn = binding.buttonBack

        chatRV = binding.rvMessages
        chatAdapter = ChatAdapter(this, viewModel.chat.value!!)
        chatAdapter.messageList = ArrayList()
        chatRV.layoutManager = LinearLayoutManager(this)
        chatRV.adapter = chatAdapter
        messagesSV = binding.svMessages
        messagesSV.post { messagesSV.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        sendBtn.setOnClickListener {
            SystemHelper.hideKeyboard(it, this)
            viewModel.sendMessage()
        }
        viewModel.messageDocs.observe(this){
            it.sortBy { msg -> msg.sentAt }
            viewModel.updateMessageData(it)
        }
        viewModel.messages.observe(this){
            chatAdapter.messageList = it
            chatAdapter.notifyDataSetChanged()
        }
    }
}