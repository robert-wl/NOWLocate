package edu.bluejack23_1.nowlocate.views

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityReportDetailBinding
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.viewModels.ReportDetailViewModel

class ReportDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportDetailBinding
    private lateinit var viewModel: ReportDetailViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var deleteBtn: ImageButton
    private lateinit var dynamicBtn: ImageButton
    private val isSelf = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ReportDetailViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()
        setContentView(binding.root)
    }

    private fun elementHandler(){
        backBtn = binding.btnBack
        deleteBtn = binding.btnDelete
        dynamicBtn = binding.btnDynamic

        if(!isSelf){
            deleteBtn.isVisible = false
            dynamicBtn.setImageResource(R.drawable.baseline_chat_black_24)
        }

        listenerHandler()
    }

    private fun listenerHandler(){
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        deleteBtn.setOnClickListener{
            viewModel.handleDelete()
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