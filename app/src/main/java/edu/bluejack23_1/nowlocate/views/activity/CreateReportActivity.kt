package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.databinding.ActivityCreateReportBinding
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.helper.ToastHelper
import edu.bluejack23_1.nowlocate.viewModels.CreateReportViewModel

class CreateReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateReportBinding
    private lateinit var viewModel: CreateReportViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var itemImageBtn: ImageButton
    private lateinit var categorySpinner: Spinner
    private lateinit var addReportBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateReportBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[CreateReportViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()

        setContentView(binding.root)
    }

    private fun elementHandler() {
        backBtn = binding.btnBack
        itemImageBtn = binding.btnItemImage
        categorySpinner = binding.spinnerCategory
        addReportBtn = binding.btnAddReport

        spinnerHandler()
        listenerHandler()
    }

    private fun spinnerHandler(){
        val categories = listOf("Electronics", "Stationary", "People", "Clothes", "Vehicles", "Pets", "Wallet", "Misc")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categorySpinner.adapter = adapter

    }

    private fun listenerHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }

        itemImageBtn.setOnClickListener {
            //handle upload image
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = categorySpinner.selectedItem.toString()
                viewModel.itemCategory.value = selectedItem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.itemCategory.value = ""
            }
        }

        addReportBtn.setOnClickListener {
            viewModel.handleCreateReport(this)
        }

        viewModel.getErrorMessage().observe(this){errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }
    }
}