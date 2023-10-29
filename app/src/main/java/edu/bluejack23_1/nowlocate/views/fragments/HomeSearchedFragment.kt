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
import edu.bluejack23_1.nowlocate.databinding.FragmentHomeSearchedBinding
import edu.bluejack23_1.nowlocate.databinding.FragmentSearchFilterBinding
import edu.bluejack23_1.nowlocate.interfaces.ViewFragment
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.viewModels.HomeViewModel
import java.util.Date

class HomeSearchedFragment(private val viewModel: HomeViewModel) : Fragment(), ViewFragment {
    private lateinit var reportSearchedRV : RecyclerView
    private lateinit var binding: FragmentHomeSearchedBinding
    private lateinit var reportAdapter : ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeSearchedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        elementHandler()
        eventHandler()
    }

    override fun elementHandler() {
        reportSearchedRV = binding.rvReportSearched

        reportAdapter = ReportAdapter(requireContext())
        reportAdapter.reportList = viewModel.reportList.value ?: ArrayList<Report>()

        reportSearchedRV.layoutManager = LinearLayoutManager(requireContext())
        reportSearchedRV.adapter = reportAdapter
    }

    override fun eventHandler() {
        //
    }
}