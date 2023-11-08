package edu.bluejack23_1.nowlocate.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityEditProfileBinding
import edu.bluejack23_1.nowlocate.databinding.ActivityRegisterBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.viewModels.EditProfileViewModel
import edu.bluejack23_1.nowlocate.viewModels.RegisterViewModel

class EditProfileActivity : AppCompatActivity(), View {

    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel
    private lateinit var genderSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton

    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        spinnerHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler() {
        genderSpinner = binding.spinnerGender
        saveButton = binding.btnSave
        backButton = binding.btnBack
    }

    override fun eventHandler() {
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = genderSpinner.selectedItem.toString()
                viewModel.gender.value = selectedItem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.gender.value = ""
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }

        backButton.setOnClickListener {
            IntentHelper.moveBack(this)
        }

        saveButton.setOnClickListener {
            viewModel.handleEditProfile()
        }

        viewModel.activityToStart.observe(this){ activityToStart ->
            IntentHelper.moveTo(this, activityToStart.java)
            return@observe
        }

    }

    private fun spinnerHandler(){
        val genders = listOf("-", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderSpinner.adapter = adapter
        genderSpinner.setSelection(genders.indexOf(authRepository.getCurrentUser().gender))
    }

}