package edu.bluejack23_1.nowlocate.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.adapter.ReportAdapter
import edu.bluejack23_1.nowlocate.databinding.FragmentHomeBinding
import edu.bluejack23_1.nowlocate.interfaces.ViewFragment
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.viewModels.HomeViewModel
import java.util.Date

class HomeFragment(private val viewModel: HomeViewModel) : Fragment(), ViewFragment {

    private lateinit var reportRV : RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var reportAdapter : ReportAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        elementHandler()
        eventHandler()

        viewModel.getData()
    }

    override fun elementHandler() {
        reportRV = binding.rvReport

        reportAdapter = ReportAdapter(requireContext())

        reportRV.layoutManager = LinearLayoutManager(requireContext())
        reportRV.adapter = reportAdapter
    }

    override fun eventHandler() {
        viewModel.reportList.observe(viewLifecycleOwner) {
            Log.wtf("HomeFragment", it.toString())
            reportAdapter.reportList = it
            reportAdapter.notifyDataSetChanged()
        }
    }
}