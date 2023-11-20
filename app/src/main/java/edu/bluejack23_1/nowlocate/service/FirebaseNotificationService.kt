package edu.bluejack23_1.nowlocate.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.repositories.AuthRepository
import edu.bluejack23_1.nowlocate.repositories.UserRepository


class FirebaseNotificationService : FirebaseMessagingService() {


    private val firebaseMessaging = FirebaseMessaging.getInstance()
    fun pushChatNotification(token: String, title: String, body: String, userId: String) {
        val data = hashMapOf(
            "title" to title,
            "body" to userId,
            "userId" to userId
        )
        val message = RemoteMessage.Builder(token)
            .setData(data)
            .build()
        firebaseMessaging.send(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.wtf("a", token)
        sendTokenToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val authRepository = AuthRepository()
        val currUser = authRepository.getCurrentUser()

        Log.wtf("a", "${remoteMessage.notification?.title}")
        Log.wtf("a", "${remoteMessage.data["title"]}")

        val title = remoteMessage.data["title"] ?: return
        val body = remoteMessage.data["body"] ?: return
        val target = remoteMessage.data["userId"] ?: return

        if (target == currUser.id) {
            return
        }

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