package edu.bluejack23_1.nowlocate.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityEditReportBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.viewModels.EditReportViewModel

class EditReportActivity : AppCompatActivity(), View {
    private lateinit var binding: ActivityEditReportBinding
    private lateinit var viewModel: EditReportViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var itemnameET: EditText
    private lateinit var categoryET: EditText
    private lateinit var shortDescriptionET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var lastseenET: EditText
    private lateinit var reportIV: ImageView
    private lateinit var saveBtn: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityEditReportBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[EditReportViewModel::class.java]
        binding.viewModel = viewModel

        Log.wtf("EditReportActivity", "bindingHandler: " + viewModel.reportImage.value)
    }

    override fun elementHandler() {
        backBtn = binding.btnBack
        itemnameET = binding.etItemName
        categoryET = binding.etCategory
        shortDescriptionET = binding.etShortDescription
        descriptionET = binding.etDescription
        lastseenET = binding.etLastSeen
        saveBtn = binding.btnSave
        reportIV = binding.ivReport

        val report = intent.getParcelableExtra("report", Report::class.java)

        if(report == null){
            IntentHelper.moveBack(this)
            return
        }

        viewModel.handleExtrasData(report)
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            handleBack()
        }

        saveBtn.setOnClickListener {
            handleSave()
        }

        viewModel.reportImage.observe(this) {
            Picasso.get().load(it).into(reportIV)
        }
    }


    private fun handleBack(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun handleSave(){

    }

}