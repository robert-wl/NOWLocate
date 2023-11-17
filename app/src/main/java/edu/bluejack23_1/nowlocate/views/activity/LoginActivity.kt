package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.databinding.ActivityLoginBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.viewModels.LoginViewModel

class LoginActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var dontHaveTV: TextView
    private lateinit var forgotPasswordTV: TextView
    private lateinit var rememberMeCB: CheckBox
    private lateinit var signInBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler() {
        dontHaveTV = binding.tvDontHave
        signInBtn = binding.btnSignIn
        rememberMeCB = binding.cbRememberMe
        forgotPasswordTV = binding.tvForgotPassword
    }

    override fun eventHandler() {
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

        viewModel.errorMessage.observe(this) { errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }

        viewModel.activityToStart.observe(this) { activityToStart ->
            Log.wtf("a", "akjsfhahjkhfasjkahhfkjhskjfhajkhfajksjhjfkashjkfashfjks")
            IntentHelper.moveToFinish(this, activityToStart.java)
        }
    }


}