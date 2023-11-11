package edu.bluejack23_1.nowlocate.views.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.databinding.ActivityCreateReportBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.models.CategoryType
import edu.bluejack23_1.nowlocate.viewModels.CreateReportViewModel

class CreateReportActivity : AppCompatActivity(), edu.bluejack23_1.nowlocate.interfaces.View {

    private lateinit var binding: ActivityCreateReportBinding
    private lateinit var viewModel: CreateReportViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var categorySpinner: Spinner
    private lateinit var addReportBtn: Button
    private lateinit var pickImageBtn: ImageButton
    private lateinit var imageView: ImageView
    private lateinit var alertDialog: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityCreateReportBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[CreateReportViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler() {
        backBtn = binding.btnBack
        categorySpinner = binding.spinnerCategory
        addReportBtn = binding.btnAddReport
        pickImageBtn = binding.btnPickImage
        imageView = binding.ivImage

        alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirmation")
        alertDialog.setMessage("Are you sure to create this report?")
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)


        spinnerHandler()
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
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
            alertDialog.show()
        }

        alertDialog.setPositiveButton("Yes"){_, _ ->
            viewModel.handleCreateReport()
        }

        alertDialog.setNegativeButton("No"){_, _ ->

        }

        viewModel.getErrorMessage().observe(this){errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }

        pickImageBtn.setOnClickListener {
            pickImageGallery()
        }

        viewModel.itemImage.observe(this){ itemImage ->
            imageView.setImageURI(itemImage)
        }

        viewModel.activityToStart.observe(this){ activityToStart ->
            val extras = viewModel.extrasParcel.value

            if(extras == null){
                IntentHelper.moveTo(this, activityToStart.java)
                return@observe
            }

            IntentHelper.moveToWithExtraFinish(this, activityToStart.java, "report", extras)
        }
    }

    private fun spinnerHandler(){
        val categories = mutableListOf("Select Category")
        categories.addAll(CategoryType.values().map { it.toString() })
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categorySpinner.adapter = adapter
    }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        changeImage.launch(intent)
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.itemImage.value = it.data?.data
            }
        }
}