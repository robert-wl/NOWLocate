package edu.bluejack23_1.nowlocate.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import edu.bluejack23_1.nowlocate.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var backBtn: ImageButton
    private lateinit var firstnameET: EditText
    private lateinit var lastnameET: EditText
    private lateinit var emailET: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        elementHandler()
    }

    private fun elementHandler() {
        backBtn = findViewById(R.id.btnBack)
        firstnameET = findViewById(R.id.etFirstName)
        lastnameET = findViewById(R.id.etLastName)
        emailET = findViewById(R.id.etEmail)
        genderSpinner = findViewById(R.id.spinnerGender)
        saveBtn = findViewById(R.id.btnSave)

        val genders = listOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderSpinner.adapter = adapter

        backBtn.setOnClickListener {
            handleBack()
        }

        saveBtn.setOnClickListener {
            handleSaveChanges()
        }

    }

    private fun handleBack() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun handleSaveChanges() {

    }

}