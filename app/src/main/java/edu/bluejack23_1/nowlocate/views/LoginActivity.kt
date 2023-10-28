package edu.bluejack23_1.nowlocate.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityLoginBinding
import edu.bluejack23_1.nowlocate.databinding.ActivityRegisterBinding
import edu.bluejack23_1.nowlocate.helper.IntentHelper
import edu.bluejack23_1.nowlocate.helper.ToastHelper
import edu.bluejack23_1.nowlocate.viewModels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var dontHaveTV: TextView
    private lateinit var forgotPasswordTV: TextView
    private lateinit var rememberMeCB: CheckBox
    private lateinit var signInBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()
        setContentView(binding.root)
    }

    private fun elementHandler() {
        dontHaveTV = binding.tvDontHave
        signInBtn = binding.btnSignIn
        rememberMeCB = binding.cbRememberMe
        forgotPasswordTV = binding.tvForgotPassword

        listenerHandler()
    }

    private fun listenerHandler(){
        signInBtn.setOnClickListener {
            viewModel.signInHandler()
        }

        dontHaveTV.setOnClickListener {
            IntentHelper.moveToFinish(this, RegisterActivity::class.java)
        }

        forgotPasswordTV.setOnClickListener {
            IntentHelper.moveTo(this, ForgotPasswordActivity::class.java)
        }

        rememberMeCB.setOnClickListener {
            viewModel.rememberMe.value = rememberMeCB.isChecked
        }

        viewModel.getErrorMessage().observe(this) { errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }
    }

}