package edu.bluejack23_1.nowlocate.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.messaging
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository
import java.util.UUID

class FirebaseNotificationService : FirebaseMessagingService() {

    private val fm = Firebase.messaging
    fun pushChatNotification(token: String, title: String, body: String, userId: String) {
        Log.wtf("PUSH NOTIFICATION", token)

        fm.send(
            RemoteMessage.Builder("cFzVN5YdSa-4jFCq39tk92:APA91bFUvvH1PYYPPSPIjBpekR1ONCRbLJxCIxHcQQjETkNyGJ230o6hrUCulQ1fPr5PcBnFwaO7eHHm9XEbNg0zZPlVovN5GvlNu_0kUiO1lZ-kzjiJc2doGWzX144KuLvjJDV1NXpE")
                .setMessageId(UUID.randomUUID().toString())
                .addData("title", title)
                .addData("body", body)
                .addData("userId", userId)
                .build()
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendTokenToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val authRepository = AuthRepository()
        val currUser = authRepository.getCurrentUser()

        Log.wtf("PUSH NOTIFICATION2222", remoteMessage.data.toString())
        val title = remoteMessage.notification?.title ?: "Title"
        val body = remoteMessage.notification?.body ?: "Body"

//        if (target == currUser.id) {
//            return
//        }



        val notificationManager = getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.baseline_chat_black_24)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel("channel_id", "edu.bluejack23_1.nowlocate", importance)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notification)
    }

    private fun sendTokenToServer(token: String) {
        val authRepository = AuthRepository()
        val userRepository = UserRepository()
        val user = authRepository.getCurrentUser()
        user.let {
            user.token = token
            userRepository.updateUserData(user)
        }
    }

}