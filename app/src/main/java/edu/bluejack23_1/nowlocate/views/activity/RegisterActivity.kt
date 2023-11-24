package edu.bluejack23_1.nowlocate.views.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.databinding.ActivityRegisterBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.StringHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.viewModels.RegisterViewModel


class RegisterActivity : AppCompatActivity(), View {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var genderSpinner: Spinner
    private lateinit var signUpButton: Button
    private lateinit var alreadyHaveTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler() {
        genderSpinner = binding.spinnerGender
        signUpButton = binding.btnSignUp
        alreadyHaveTV = binding.tvAlreadyHave


        spinnerHandler()
    }

    override fun eventHandler() {
        signUpButton.setOnClickListener {
            viewModel.signUpHandler()
        }

        alreadyHaveTV.setOnClickListener {
            IntentHelper.moveToFinish(this, LoginActivity::class.java)
        }

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

        viewModel.activityToStart.observe(this) { activityToStart ->
            IntentHelper.moveToFinish(this, activityToStart.java)
        }
    }

    private fun spinnerHandler(){
        val genders = listOf("-", StringHelper.getString(R.string.male), StringHelper.getString(R.string.female), StringHelper.getString(
            R.string.female))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderSpinner.adapter = adapter
    }

}