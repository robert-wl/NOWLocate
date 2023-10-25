package edu.bluejack23_1.nowlocate.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityChangePasswordBinding
import edu.bluejack23_1.nowlocate.databinding.ActivityLoginBinding
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.helper.ToastHelper
import edu.bluejack23_1.nowlocate.viewModels.ChangePasswordViewModel
import edu.bluejack23_1.nowlocate.viewModels.LoginViewModel

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()

        setContentView(binding.root)

    }

    private fun elementHandler(){
        saveBtn = binding.btnSave
        backBtn = binding.btnBack
        listenerHandler()

    }

    private fun listenerHandler(){
        saveBtn.setOnClickListener {
            viewModel.handleChangePassword()
        }
        backBtn.setOnClickListener{
            IntentHelper.moveBack(this)
        }
        viewModel.getErrorMessage().observe(this){errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }
    }

}