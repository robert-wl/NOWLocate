package edu.bluejack23_1.nowlocate.views.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.databinding.ActivityChangePasswordBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.viewModels.ChangePasswordViewModel

class ChangePasswordActivity : AppCompatActivity(), View {

    private lateinit var backBtn: ImageButton
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var saveBtn: Button

    private lateinit var alertDialog: AlertDialog.Builder

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
        backBtn = binding.buttonBack
        saveBtn = binding.buttonSave

        alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirmation")
        alertDialog.setMessage("Are you sure to change your password?")
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        saveBtn.setOnClickListener {
            alertDialog.show()
        }
        viewModel.errorMessage.observe(this){errorMsg ->
            ToastHelper.showMessage(this, errorMsg)
        }
        alertDialog.setPositiveButton("Yes"){_, _ ->
            viewModel.handleChangePassword()
        }

        alertDialog.setNegativeButton("No"){_, _ ->

        }
    }

}