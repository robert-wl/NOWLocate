package edu.bluejack23_1.nowlocate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.views.viewHolders.FilterViewHolder

class FilterAdapter(private var context: Context) : RecyclerView.Adapter<FilterViewHolder>() {
    lateinit var filterList : ArrayList<Filter>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.filter_card, parent, false)
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
//        holder.reportImageIV.set(reportList.get(position).reportImage)
        holder.filterNameTV.text = filterList[position].filterName
        holder.filterAmountTV.text = filterList[position].filterItemCount.toString()
        holder.filterIconIV.setImageResource(R.drawable.baseline_add_24)
    }
}