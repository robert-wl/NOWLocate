package edu.bluejack23_1.nowlocate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.Message
import edu.bluejack23_1.nowlocate.views.viewHolders.ChatViewHolder

class ChatAdapter (private var context: Context, private val chat: Chat): Adapter<ChatViewHolder>() {
    lateinit var messageList: ArrayList<Message>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_card, parent, false)
        return ChatViewHolder(view, chat)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.setData(messageList[position])
        holder.eventHandler(context)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

}