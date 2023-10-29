package edu.bluejack23_1.nowlocate.views.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import edu.bluejack23_1.nowlocate.R

class ReportViewHolder : ViewHolder {
    var reportImageIV : ImageView
    var reportNameTV : TextView
    var reportCategoryTV : TextView
    var reportShortDescriptionTV : TextView

    constructor(itemView: View) : super(itemView) {
        reportImageIV = itemView.findViewById(R.id.ivReportImage)
        reportNameTV = itemView.findViewById(R.id.tvReportName)
        reportCategoryTV = itemView.findViewById(R.id.tvReportCategory)
        reportShortDescriptionTV = itemView.findViewById(R.id.tvReportShortDescription)
    }




}