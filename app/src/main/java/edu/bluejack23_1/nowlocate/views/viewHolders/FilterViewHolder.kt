package edu.bluejack23_1.nowlocate.views.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import edu.bluejack23_1.nowlocate.R

class FilterViewHolder : ViewHolder {

    var filterIconIV : ImageView
    var filterNameTV : TextView
    var filterAmountTV : TextView
    constructor(itemView: View) : super(itemView) {
        filterIconIV = itemView.findViewById(R.id.ivFilterIcon)
        filterNameTV = itemView.findViewById(R.id.tvFilterName)
        filterAmountTV = itemView.findViewById(R.id.tvFilterAmount)
    }
}