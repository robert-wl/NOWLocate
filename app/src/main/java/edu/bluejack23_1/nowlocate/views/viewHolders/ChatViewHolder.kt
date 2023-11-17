package edu.bluejack23_1.nowlocate.views.viewHolders

import android.content.Context
import android.text.Layout.Alignment
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.Message

class ChatViewHolder : ViewHolder {

    private var chatImageView: ImageView
    private var chatMessage: TextView
    private var messageLinearLayout: LinearLayout
    private lateinit var message: Message
    private var view: View
    private var chat: Chat

    constructor(itemView: View, chat: Chat) : super(itemView) {
        chatImageView = itemView.findViewById(R.id.ivChatImage)
        chatMessage = itemView.findViewById(R.id.tvChatMessage)
        messageLinearLayout = itemView.findViewById(R.id.llMessage)
        view = itemView
        this.chat = chat
    }

    fun setData(message: Message) {
        this.message = message
        this.chatMessage.text = message.message
        if(message.sender == chat.sender){
            chatImageView.isVisible = false
            messageLinearLayout.gravity = Gravity.END
        }
//        Picasso.get().load(message.sender.image).into(this.chatImageView)
    }

    fun eventHandler(context: Context){

    }

}