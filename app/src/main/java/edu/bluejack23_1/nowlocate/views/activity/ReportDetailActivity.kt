package edu.bluejack23_1.nowlocate.views.activity

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.databinding.ActivityReportDetailBinding
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.StringHelper
import edu.bluejack23_1.nowlocate.interfaces.View
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.viewModels.ReportDetailViewModel

class ReportDetailActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityReportDetailBinding
    private lateinit var viewModel: ReportDetailViewModel
    private lateinit var backBtn: ImageButton
    private lateinit var deleteBtn: ImageButton
    private lateinit var dynamicBtn: ImageButton
    private lateinit var reportIV: ImageView
    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var profileCIV: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHandler()
        elementHandler()
        eventHandler()

        setContentView(binding.root)
    }

    override fun bindingHandler() {
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ReportDetailViewModel::class.java]
        binding.viewModel = viewModel
    }

    override fun elementHandler(){
        backBtn = binding.buttonBack
        deleteBtn = binding.btnDelete
        dynamicBtn = binding.btnDynamic
        reportIV = binding.imageViewReportImage
        profileCIV = binding.circleImageViewProfileImage

        alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(StringHelper.getString(R.string.confirmation))
        alertDialog.setMessage(StringHelper.getString(R.string.delete_report_confirmation))
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)


        val report = intent.getParcelableExtra("report", Report::class.java)

        if(report == null){
            IntentHelper.moveBack(this)
            return
        }

        viewModel.handleExtrasData(report)
    }

    override fun eventHandler() {
        backBtn.setOnClickListener {
            IntentHelper.moveBack(this)
        }
        deleteBtn.setOnClickListener{
            alertDialog.show()
        }

        viewModel.reportImage.observe(this) {
            Picasso.get().load(it).into(reportIV)
        }

        viewModel.userImage.observe(this) {
            Picasso.get().load(it).placeholder(R.drawable.baseline_person_black_24).into(profileCIV)
        }

        viewModel.isSelf.observe(this) { isSelf ->
            if(isSelf) {
                deleteBtn.isVisible = true
            } else {
                deleteBtn.isVisible = false
                dynamicBtn.setImageResource(R.drawable.baseline_chat_black_24)
            }
        }

        dynamicBtn.setOnClickListener {
            if(viewModel.isSelf.value == true){
                IntentHelper.moveToWithExtra(this, EditReportActivity::class.java, "report", viewModel.report.value!!)
            } else {
                viewModel.handleMoveToConversation(this)
            }
        }

        viewModel.activityToStart.observe(this) { activityToStart ->
            IntentHelper.moveToFinish(this, activityToStart.java)
        }

        alertDialog.setPositiveButton(StringHelper.getString(R.string.yes)){_, _ ->
            viewModel.handleDelete()
        }

        alertDialog.setNegativeButton(StringHelper.getString(R.string.no)){_, _ ->

        }

        profileCIV.setOnClickListener{
            viewModel.handleMoveToProfile(this)
        }

    }

}