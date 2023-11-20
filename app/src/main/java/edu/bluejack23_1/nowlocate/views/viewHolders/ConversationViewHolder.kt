package edu.bluejack23_1.nowlocate.views.viewHolders

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.views.activity.ChatActivity
import java.text.DateFormat

class ConversationViewHolder : ViewHolder {

    private var conversationCardView: CardView
    private var conversationImageView: ImageView
    private var conversationFirstName: TextView
    private var conversationLastName: TextView
    private var conversationLastMessage: TextView
    private var conversationLastTime: TextView
    private lateinit var chat: Chat
    private var view: View

    constructor(itemView: View) : super(itemView) {
        conversationCardView = itemView.findViewById(R.id.cvConversationCard)
        conversationImageView = itemView.findViewById(R.id.image_view_profile_image)
        conversationFirstName = itemView.findViewById(R.id.text_view_first_name)
        conversationLastName = itemView.findViewById(R.id.text_view_last_name)
        conversationLastMessage = itemView.findViewById(R.id.text_view_last_message)
        conversationLastTime = itemView.findViewById(R.id.text_view_last_time)
        view = itemView
    }

    fun setData(chat: Chat) {
        this.chat = chat

        Log.wtf("chat", chat.recipient.toString())
        conversationFirstName.text = chat.recipient.firstName
        conversationLastName.text = chat.recipient.lastName
        conversationLastMessage.text = chat.lastMessage
        conversationLastTime.text =
            DateFormat.getTimeInstance(DateFormat.SHORT).format(chat.lastTime)


        Picasso.get().load(Uri.parse(chat.recipient.image)).placeholder(R.drawable.baseline_person_black_24).into(this.conversationImageView)
    }

    fun eventHandler(context: Context) {
        conversationCardView.setOnClickListener {
            IntentHelper.moveToWithExtra(context, ChatActivity::class.java, "chat", this.chat)
        }
    }

}