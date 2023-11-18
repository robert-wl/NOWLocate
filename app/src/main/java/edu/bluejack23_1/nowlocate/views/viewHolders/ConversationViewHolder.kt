package edu.bluejack23_1.nowlocate.views.viewHolders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.User
import edu.bluejack23_1.nowlocate.views.activity.ChatActivity

class ConversationViewHolder: ViewHolder {

    private var conversationCV: CardView
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
        conversationCV = itemView.findViewById(R.id.cvConversationCard)
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

//        Picasso.get().load(chat.recipient.image).into(this.conversationImageView)
    }

    fun eventHandler(context: Context){
        conversationCV.setOnClickListener {
            IntentHelper.moveToWithExtra(context, ChatActivity::class.java, "chat", this.chat)
        }
    }

}