package edu.bluejack23_1.nowlocate.views.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityEditProfileBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.interfaces.GalleryAccess
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.viewModels.EditProfileViewModel

class EditProfileActivity : AppCompatActivity(), View, GalleryAccess {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel
    private lateinit var profileImageCIV: CircleImageView
    private lateinit var genderSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var changePasswordLL: LinearLayout

    private lateinit var alertDialog: AlertDialog.Builder

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
        saveButton = binding.buttonSave
        backButton = binding.buttonBack
        profileImageCIV = binding.circleImageViewProfileImage
        changePasswordLL = binding.linearLayoutChangePassword

        alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirmation")
        alertDialog.setMessage("Are you sure to edit your profile?")
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)

    }

    override fun eventHandler() {
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long
            ) {
                val selectedItem = genderSpinner.selectedItem.toString()
                viewModel.gender.value = selectedItem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.gender.value = ""
            }
        }

        alertDialog.setPositiveButton("Yes") { _, _ ->
            viewModel.handleEditProfile()
        }

        alertDialog.setNegativeButton("No") { _, _ ->

        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }

        backButton.setOnClickListener {
            IntentHelper.moveBack(this)
        }

        saveButton.setOnClickListener {
            alertDialog.show()
        }

        viewModel.image.observe(this) {
            Picasso.get().load(it).placeholder(R.drawable.baseline_person_24).into(profileImageCIV)
        }

        viewModel.activityToStart.observe(this) { activityToStart ->
            IntentHelper.moveTo(this, activityToStart.java)
            return@observe
        }

        changePasswordLL.setOnClickListener {
            IntentHelper.moveTo(this, ChangePasswordActivity::class.java)
        }

        profileImageCIV.setOnClickListener {
            pickImageGallery()
        }

    }

    private fun spinnerHandler() {
        val genders = listOf("-", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderSpinner.adapter = adapter
        genderSpinner.setSelection(genders.indexOf(authRepository.getCurrentUser().gender))
    }

    override val changeImage: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            viewModel.image.value = it.data?.data
        }
    }

}