package edu.bluejack23_1.nowlocate.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.ValidationHelper

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var backBtn: ImageButton
    private lateinit var passwordET: EditText
    private lateinit var confirmpasswordET: EditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        handleElement()

    }

    private fun handleElement(){
        backBtn = findViewById(R.id.btnBack)
        passwordET = findViewById(R.id.etPassword)
        confirmpasswordET = findViewById(R.id.etConfirmPassword)
        saveBtn = findViewById(R.id.btnSave)

        backBtn.setOnClickListener {
            handleBack()
        }

        saveBtn.setOnClickListener {
            handleSave()
        }

    }

    private fun handleBack(){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun handleSave(){
        val password = passwordET.text.toString()
        val confirmPassword = confirmpasswordET.text.toString()

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