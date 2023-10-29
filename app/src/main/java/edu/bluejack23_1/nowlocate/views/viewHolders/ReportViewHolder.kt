package edu.bluejack23_1.nowlocate.views.viewHolders

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.views.activity.ReportDetailActivity
import java.text.DateFormat

class ReportViewHolder : ViewHolder {
    private var reportCV: CardView
    private var reportImageIV: ImageView
    private var reportNameTV: TextView
    private var reportCategoryTV: TextView
    private var reportShortDescriptionTV: TextView
    private var reportDateTV: TextView
    private var view: View
    lateinit var report: Report

    constructor(itemView: View) : super(itemView) {
        reportImageIV = itemView.findViewById(R.id.ivReportImage)
        reportNameTV = itemView.findViewById(R.id.tvReportName)
        reportCategoryTV = itemView.findViewById(R.id.tvReportCategory)
        reportShortDescriptionTV = itemView.findViewById(R.id.tvReportShortDescription)
        reportDateTV = itemView.findViewById(R.id.tvReportDate)
        reportCV = itemView.findViewById(R.id.cvReport)
        view = itemView
    }

    fun setData(report: Report){
        this.report = report
        this.reportNameTV.text = report.name
        this.reportCategoryTV.text = report.category
        this.reportShortDescriptionTV.text = report.shortDescription
        this.reportDateTV.text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(report.reportDate)

        Picasso.get().load(report.image).into(this.reportImageIV)
    }

     fun eventHandler(context: Context){
        reportCV.setOnClickListener {
            IntentHelper.moveToWithExtra(context, ReportDetailActivity::class.java, "report", this.report)
        }
    }




}