package edu.bluejack23_1.nowlocate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.views.viewHolders.ReportViewHolder

class ReportAdapter(private var context: Context) : Adapter<ReportViewHolder>() {
    lateinit var reportList : ArrayList<Report>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.report_card, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.setData(reportList[position])
        holder.eventHandler(context)
    }
}