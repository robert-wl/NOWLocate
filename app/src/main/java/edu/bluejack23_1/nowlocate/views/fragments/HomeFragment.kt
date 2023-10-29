package edu.bluejack23_1.nowlocate.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.adapters.ReportAdapter
import edu.bluejack23_1.nowlocate.databinding.FragmentHomeBinding
import edu.bluejack23_1.nowlocate.interfaces.ViewFragment
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.viewModels.HomeFragmentViewModel

class HomeFragment : Fragment(), ViewFragment {

    private lateinit var reportRV : RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var reportAdapter : ReportAdapter
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var reportPB: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()
        eventHandler()

        viewModel.getData()
    }

    override fun elementHandler() {
        reportRV = binding.rvReport
        reportPB = binding.pbReport

        reportAdapter = ReportAdapter(requireContext())
        reportAdapter.reportList = ArrayList<Report>()
        reportRV.layoutManager = LinearLayoutManager(requireContext())
        reportRV.adapter = reportAdapter
    }

    override fun eventHandler() {
        viewModel.reportList.observe(viewLifecycleOwner) {
            Log.wtf("HomeFragment", it.toString())
            reportAdapter.reportList = it
            reportAdapter.notifyDataSetChanged()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if(it){
                reportPB.visibility = View.VISIBLE
                return@observe
            }

            reportPB.visibility = View.GONE
        }

        reportRV.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = reportAdapter.itemCount

                if(visibleItemCount + pastVisibleItem >= total && !viewModel.isLoading.value!!){
                    viewModel.page++
                    viewModel.getData()
                }
            }
        })
    }
}