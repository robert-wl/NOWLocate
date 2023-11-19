package edu.bluejack23_1.nowlocate.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.models.Chat
import edu.bluejack23_1.nowlocate.models.Report
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.ChatRepository
import edu.bluejack23_1.nowlocate.repositories.ReportRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import edu.bluejack23_1.nowlocate.views.activity.ChatActivity
import edu.bluejack23_1.nowlocate.views.activity.ConversationActivity
import edu.bluejack23_1.nowlocate.views.activity.HomeActivity
import edu.bluejack23_1.nowlocate.views.activity.ProfileActivity
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.UUID
import kotlin.reflect.KClass

class ReportDetailViewModel : ViewModel() {

    var isSelf = MutableLiveData<Boolean>()
    var report = MutableLiveData<Report>()
    var reportDate = MutableLiveData<String>()
    var reportImage = MutableLiveData<Uri>()
    var name = MutableLiveData<String>()
    var id = MutableLiveData<String>()
    val activityToStart = MutableLiveData<KClass<*>>()

    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()
    private val reportRepository = ReportRepository()
    private val chatRepository = ChatRepository()

    fun handleExtrasData(reportData: Report) {
        report.value = reportData
        reportImage.value = Uri.parse(reportData.image)
        reportDate.value =
            DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(reportData.reportDate)

        viewModelScope.launch {
            val result = report.value?.reportedBy?.let {
                userRepository.getUser(it)
            }

            if (result?.isFailure == true) {
                return@launch
            }

            val user = result?.getOrNull()

            name.value = user?.firstName + " " + user?.lastName
            isSelf.value = authRepository.isSelf(user?.id)
            id.value = user?.id
        }
    }

    fun handleMoveToConversation(context: Context) {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            val result = chatRepository.addChat(id.value!!, user.id)
            IntentHelper.moveToWithExtra(context, ChatActivity::class.java, "chat", result)
        }
    }

    fun handleDelete() {
        reportRepository.deleteReport(report.value?.id!!)
        activityToStart.value = HomeActivity::class
    }

    fun handleMoveToProfile(context: Context){
        viewModelScope.launch {
            if(isSelf.value!!){
                IntentHelper.moveToWithExtra(context, ProfileActivity::class.java, "user", authRepository.getCurrentUser())
            } else {
                val result = report.value?.reportedBy?.let {
                    userRepository.getUser(it)
                }

                if (result?.isFailure == true) {
                    return@launch
                }

                val user = result?.getOrNull()
                IntentHelper.moveToWithExtra(context, ProfileActivity::class.java, "user", user!!)
            }
        }
    }

}