package edu.bluejack23_1.nowlocate.views.viewHolders

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.Message

class ChatViewHolder(itemView: View, private var chat: Chat) : ViewHolder(itemView) {

    private var receiverImageView: ImageView
    private var senderImageView: ImageView
    private var chatMessage: TextView
    private var messageLinearLayout: LinearLayout
    private lateinit var message: Message

    init {
        receiverImageView = itemView.findViewById(R.id.image_view_receiver)
        senderImageView = itemView.findViewById(R.id.image_view_sender)
        chatMessage = itemView.findViewById(R.id.text_view_chat_message)
        messageLinearLayout = itemView.findViewById(R.id.linear_layout_message)
    }

    fun setData(message: Message) {
        this.message = message
        chatMessage.text = message.message
        if(message.sender == chat.sender){
            receiverImageView.isVisible = true
            senderImageView.isVisible = false
            messageLinearLayout.gravity = Gravity.END or Gravity.CENTER
            chatMessage.setBackgroundColor(Color.parseColor("#30C3CD"))
            chatMessage.setTextColor(Color.parseColor("#FFFFFF"))
            Picasso.get().load(chat.sender.image).into(receiverImageView)
        }
        else {
            senderImageView.isVisible = true
            receiverImageView.isVisible = false
            messageLinearLayout.gravity = Gravity.START or Gravity.CENTER
            chatMessage.setBackgroundColor(Color.parseColor("#F4F4F4"))
            chatMessage.setTextColor(Color.parseColor("#000000"))
            Picasso.get().load(chat.recipient.image).into(senderImageView)
        }
//        Picasso.get().load(message.sender.image).into(this.chatImageView)
    }

    fun eventHandler(context: Context){

    }

}