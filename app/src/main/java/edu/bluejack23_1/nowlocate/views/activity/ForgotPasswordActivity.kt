package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.databinding.ActivityForgotPasswordBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.viewModels.ForgotPasswordViewModel

class ForgotPasswordActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var resetBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler(){
        backBtn = binding.btnBack
        resetBtn = binding.btnForgotPassword
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        resetBtn.setOnClickListener {
            viewModel.handleResetPassword(this)
        }
        viewModel.getErrorMessage().observe(this){ errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }
    }


}