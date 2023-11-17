package edu.bluejack23_1.nowlocate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.views.viewHolders.ConversationViewHolder

class ConversationAdapter(private var context: Context): Adapter<ConversationViewHolder>() {
    lateinit var conversationList: ArrayList<Chat>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.conversation_card, parent, false)
        return ConversationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return conversationList.size
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.setData(conversationList[position])
        holder.eventHandler(context)
    }

    fun setData(data: ArrayList<Chat>){
        this.conversationList = data
    }

}