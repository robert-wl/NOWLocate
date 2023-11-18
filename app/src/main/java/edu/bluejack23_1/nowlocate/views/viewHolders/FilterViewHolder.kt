package edu.bluejack23_1.nowlocate.views.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import edu.bluejack23_1.nowlocate.R

class FilterViewHolder : ViewHolder {

    var filterIconIV: ImageView
    var filterNameTV: TextView
    var filterAmountTV: TextView
    var filterCardCV: CardView
    constructor(itemView: View) : super(itemView) {
        filterIconIV = itemView.findViewById(R.id.tv_filter_icon)
        filterNameTV = itemView.findViewById(R.id.tv_filter_name)
        filterAmountTV = itemView.findViewById(R.id.tv_filter_amount)
        filterCardCV = itemView.findViewById(R.id.cvFilterCard)
    }
}