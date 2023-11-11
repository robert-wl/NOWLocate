package edu.bluejack23_1.nowlocate.views.viewHolders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.User

class ConversationViewHolder: ViewHolder {

    private var conversationImageView: ImageView
    private var conversationFirstName: TextView
    private var conversationLastName: TextView
    private var conversationLastMessage: TextView
    private var conversationLastTime: TextView
    private var view: View
    private lateinit var chat: Chat
    private lateinit var sender: User
    private lateinit var recipient: User

    constructor(itemView: View): super(itemView) {
        conversationImageView = itemView.findViewById(R.id.ivConversationImage)
        conversationFirstName = itemView.findViewById(R.id.tvConversationFirstName)
        conversationLastName = itemView.findViewById(R.id.tvConversationLastName)
        conversationLastMessage = itemView.findViewById(R.id.tvConversationLastMessage)
        conversationLastTime = itemView.findViewById(R.id.tvConversationLastTime)
        view = itemView
    }

    fun setData(chat: Chat){
        this.chat = chat
        this.sender = chat.sender
        this.recipient = chat.recipient
        this.conversationFirstName.text = recipient.firstName
        this.conversationLastName.text = recipient.lastName
        this.conversationLastMessage.text = chat.lastMessage
        this.conversationLastTime.text = chat.lastTime.toLocaleString()

    }

    fun eventHandler(context: Context){

    }

}