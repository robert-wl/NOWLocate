package edu.bluejack23_1.nowlocate.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityChangePasswordBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.helpers.ValidationHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.viewModels.ChangePasswordViewModel

class ChangePasswordActivity : AppCompatActivity(), View {

    private lateinit var backBtn: ImageButton
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler() {
        backBtn = binding.btnBack
        saveBtn = binding.btnSave
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        saveBtn.setOnClickListener {
            viewModel.handleChangePassword()
        }
        viewModel.errorMessage.observe(this){errorMsg ->
            ToastHelper.showMessage(this, errorMsg)
        }
    }

}