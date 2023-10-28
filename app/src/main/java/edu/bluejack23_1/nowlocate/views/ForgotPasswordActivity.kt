package edu.bluejack23_1.nowlocate.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityForgotPasswordBinding
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.helper.ToastHelper
import edu.bluejack23_1.nowlocate.viewModels.ForgotPasswordViewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var resetBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()
        setContentView(binding.root)
    }

    private fun elementHandler(){
        backBtn = binding.btnBack
        resetBtn = binding.btnForgotPassword
        listenerHandler()
    }

    private fun listenerHandler(){
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        resetBtn.setOnClickListener {
            viewModel.handleResetPassword()
        }
        viewModel.getErrorMessage().observe(this){ errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }
    }

}