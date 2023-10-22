package edu.bluejack23_1.nowlocate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import edu.bluejack23_1.nowlocate.databinding.ActivityRegisterBinding
import edu.bluejack23_1.nowlocate.helper.ValidationHelper
import edu.bluejack23_1.nowlocate.viewModels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
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

        binding.viewModel = registerViewModel
        binding.lifecycleOwner = this@RegisterActivity

        val view = binding.root

        setContentView(view)

//        elementHandler()

    }

    private fun elementHandler(){
        firstnameET = findViewById(R.id.etFirstName)
        lastnameET = findViewById(R.id.etLastName)
        emailET = findViewById(R.id.etEmail)
        usernameET = findViewById(R.id.etUsername)
        passwordET = findViewById(R.id.etPassword)
        confirmPasswordET = findViewById(R.id.etConfirmPassword)
        genderSpinner = findViewById(R.id.spinnerGender)
        signUpButton = findViewById(R.id.btnSignUp)
        alreadyHaveTV = findViewById(R.id.tvAlreadyHave)

        val genders = listOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderSpinner.adapter = adapter

        signUpButton.setOnClickListener {
            signUpHandler()
        }

        alreadyHaveTV.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
        }

    }

    private fun signUpHandler(){
        val firstName = firstnameET.text.toString()
        val lastName = lastnameET.text.toString()
        val email = emailET.text.toString()
        val username = usernameET.text.toString()
        val password = passwordET.text.toString()
        val confirmPassword = confirmPasswordET.text.toString()
        val gender = genderSpinner.selectedItem.toString()

        if(firstName.isEmpty()){

            return
        }

        if(lastName.isEmpty()){

            return
        }

        if(email.isEmpty()){

            return
        }

        if(username.isEmpty()){

            return
        }

        if(password.isEmpty()){

            return
        }

        if(confirmPassword.isEmpty()){

            return
        }

        if(!ValidationHelper.isAlphaNumeric(password)){

            return
        }

        if(password != confirmPassword){

            return
        }

    }

}