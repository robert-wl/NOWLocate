package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityReportDetailBinding
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.viewModels.ReportDetailViewModel

class ReportDetailActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityReportDetailBinding
    private lateinit var viewModel: ReportDetailViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var deleteBtn: ImageButton
    private lateinit var dynamicBtn: ImageButton
    private lateinit var reportIV: ImageView
    private val isSelf = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ReportDetailViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler(){
        backBtn = binding.btnBack
        deleteBtn = binding.btnDelete
        dynamicBtn = binding.btnDynamic
        reportIV = binding.ivImage


       val report = intent.getParcelableExtra("report", Report::class.java)

        if(report == null){
            IntentHelper.moveBack(this)
            return
        }

        viewModel.handleExtrasData(report)

        if(!isSelf){
            deleteBtn.isVisible = false
            dynamicBtn.setImageResource(R.drawable.baseline_chat_black_24)
        }

    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        deleteBtn.setOnClickListener{
            viewModel.handleDelete()
        }

        viewModel.reportImage.observe(this) { reportImage ->
            Log.wtf("reportImage", reportImage.toString())
            Picasso.get().load(reportImage).into(reportIV)
        }

        if(isSelf) {
            dynamicBtn.setOnClickListener {
//                IntentHelper.moveTo(ChatActivity::class.java)
            }
        } else {
            dynamicBtn.setOnClickListener {
//                IntentHelper.moveTo(EditReportActivity::class.java)
            }
        }
    }

}