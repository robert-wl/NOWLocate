package edu.bluejack23_1.nowlocate.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.adapters.FilterAdapter
import edu.bluejack23_1.nowlocate.databinding.FragmentSearchFilterBinding
import edu.bluejack23_1.nowlocate.interfaces.ViewFragment
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.viewModels.HomeViewModel

class SearchFilterFragment(private val viewModel: HomeViewModel) : Fragment(), ViewFragment {
    private lateinit var filterRV : RecyclerView
    private lateinit var binding: FragmentSearchFilterBinding
    private lateinit var filterAdapter : FilterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        elementHandler()
        eventHandler()
    }

    override fun elementHandler() {
        filterRV = binding.rvFilter

        filterAdapter = FilterAdapter(requireContext())
        filterAdapter.filterList = viewModel.filterList.value ?: ArrayList<Filter>()

        filterRV.layoutManager = GridLayoutManager(requireContext(), 2)
        filterRV.adapter = filterAdapter
    }

    override fun eventHandler() {
        //
    }

}