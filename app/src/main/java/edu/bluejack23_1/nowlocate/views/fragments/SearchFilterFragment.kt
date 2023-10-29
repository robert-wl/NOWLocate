package edu.bluejack23_1.nowlocate.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.adapter.FilterAdapter
import edu.bluejack23_1.nowlocate.models.Filter

class SearchFilterFragment : Fragment() {
    private lateinit var filterRV : RecyclerView
    private lateinit var filterAdapter : FilterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterRV = view.findViewById(R.id.rvFilter)

        val filterArraylist = ArrayList<Filter>()

        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))
        filterArraylist.add(Filter("Filter 1", 2))

        filterAdapter = FilterAdapter(view.context!!)
        filterAdapter.filterList = filterArraylist

        filterRV.layoutManager = GridLayoutManager(view.context, 2)
        filterRV.adapter = filterAdapter
    }

}