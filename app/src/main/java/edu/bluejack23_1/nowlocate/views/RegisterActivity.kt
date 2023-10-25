package edu.bluejack23_1.nowlocate.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.databinding.ActivityRegisterBinding
import edu.bluejack23_1.nowlocate.helper.ToastHelper
import edu.bluejack23_1.nowlocate.viewModels.RegisterViewModel


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var firstnameET: EditText
    private lateinit var lastnameET: EditText
    private lateinit var emailET: EditText
    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText
    private lateinit var confirmPasswordET: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var signUpButton: Button
    private lateinit var alreadyHaveTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.viewModel = viewModel

        elementHandler()
        setContentView(binding.root)

        viewModel.getErrorMessage().observe(this) { errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }

    }

    private fun elementHandler() {
        firstnameET = binding.etFirstName
        lastnameET = binding.etLastName
        emailET = binding.etEmail
        usernameET = binding.etUsername
        passwordET = binding.etPassword
        confirmPasswordET = binding.etConfirmPassword
        genderSpinner = binding.spinnerGender
        signUpButton = binding.btnSignUp
        alreadyHaveTV = binding.tvAlreadyHave

        spinnerHandler()
        buttonHandler()

    }

    private fun spinnerHandler(){
        val genders = listOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderSpinner.adapter = adapter
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = genderSpinner.selectedItem.toString()
                viewModel.gender.value = selectedItem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.gender.value = ""
            }
        }

    }

    private fun buttonHandler(){
        signUpButton.setOnClickListener {
            viewModel.signUpHandler()
        }

        alreadyHaveTV.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}