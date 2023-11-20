package edu.bluejack23_1.nowlocate.views.activity

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import edu.bluejack23_1.nowlocate.databinding.ActivityEditReportBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.ToastHelper
import edu.bluejack23_1.nowlocate.interfaces.GalleryAccess
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.models.CategoryType
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.viewModels.EditReportViewModel

class EditReportActivity : AppCompatActivity(), View, GalleryAccess {
    private lateinit var binding: ActivityEditReportBinding
    private lateinit var viewModel: EditReportViewModel
    private lateinit var backButton: ImageButton
    private lateinit var itemNameEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var shortDescriptionEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var lastSeenEditText: EditText
    private lateinit var pickImageButton: ImageButton
    private lateinit var saveButton: ImageButton
    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var profileImageView: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityEditReportBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[EditReportViewModel::class.java]
        binding.viewModel = viewModel

        Log.wtf("EditReportActivity", "bindingHandler: " + viewModel.reportImage.value)
    }

    override fun elementHandler() {
        backButton = binding.buttonBack
        itemNameEditText = binding.editTextItemName
        categorySpinner = binding.spinnerCategory
        shortDescriptionEditText = binding.editTextShortDescription
        descriptionEditText = binding.editTextDescription
        lastSeenEditText = binding.editTextLastSeen
        saveButton = binding.buttonSave
        pickImageButton = binding.buttonPickImage
        profileImageView = binding.circleImageViewProfileImage

        alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirmation")
        alertDialog.setMessage("Are you sure to save this report?")
        alertDialog.setIcon(R.drawable.ic_dialog_alert)

        val report = intent.getParcelableExtra("report", Report::class.java)

        if (report == null) {
            IntentHelper.moveBack(this)
            return
        }

        viewModel.handleExtrasData(report)
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveTo(this, HomeActivity::class.java, false)
        }

        saveBtn.setOnClickListener {
            alertDialog.show()
        }

        viewModel.reportImage.observe(this) {
            Picasso.get().load(it).into(reportIV)
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            ToastHelper.showMessage(this, errorMessage)
        }

        alertDialog.setPositiveButton("Yes"){_, _ ->
            Log.wtf("EditReportActivity", "eventHandler: " + viewModel.reportImage.value)
            viewModel.handleEditReport()
        }

        alertDialog.setNegativeButton("No"){_, _ ->

        }
        profileIV.setOnClickListener{
            viewModel.handleMoveToProfile(this)
        }
    }

}