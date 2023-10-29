package edu.bluejack23_1.nowlocate.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.adapter.ReportAdapter
import edu.bluejack23_1.nowlocate.models.Report
import java.util.Date

class HomeFragment : Fragment() {

    private lateinit var reportRV : RecyclerView
    private lateinit var reportAdapter : ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportRV = view.findViewById(R.id.rvReport)


        val reportArraylist = ArrayList<Report>()
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))
        reportArraylist.add(Report("Report 1", "Category 1", "Short Description 1", "a", "a",  Date()))

        reportAdapter = ReportAdapter(view.context!!)
        reportAdapter.reportList = reportArraylist

        reportRV.layoutManager = LinearLayoutManager(view.context)
        reportRV.adapter = reportAdapter

    }
}