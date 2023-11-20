package edu.bluejack23_1.nowlocate.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.adapters.ReportAdapter
import edu.bluejack23_1.nowlocate.databinding.FragmentProfileBinding
import edu.bluejack23_1.nowlocate.interfaces.ViewFragment
import edu.bluejack23_1.nowlocate.viewModels.ProfileViewModel


class ProfileFragment(private val viewModelPassed: ProfileViewModel) : Fragment(), ViewFragment {
    private lateinit var reportRV: RecyclerView
    private lateinit var binding: FragmentProfileBinding
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var viewModel: ProfileViewModel
    private lateinit var reportPB: ProgressBar
    private lateinit var orderByTV: TextView
    private lateinit var orderByIV: ImageView
    private var isAscending = false;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel = viewModelPassed

        elementHandler()
        eventHandler()

    }
    override fun elementHandler() {
        reportRV = binding.rvReport
        reportPB = binding.pbReport
        orderByTV = binding.tvOrderBy
        orderByIV = binding.ivOrderBy

        reportAdapter = ReportAdapter(requireContext())
        reportAdapter.reportList = ArrayList()
        reportRV.layoutManager = LinearLayoutManager(requireContext())
        reportRV.adapter = reportAdapter
    }

    override fun eventHandler() {
        viewModel.isAscending.observe(viewLifecycleOwner){
            viewModel.getData()
        }

        viewModel.reportList.observe(viewLifecycleOwner) {
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

        orderByIV.setOnClickListener {
            val nextValue = !viewModel.isAscending.value!!
            viewModel.isAscending.value = nextValue

            if(nextValue){
                orderByIV.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                orderByTV.text = "Ascending"
            } else {
                orderByIV.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                orderByTV.text = "Descending"
            }

//            viewModel.getData()
        }
    }


}