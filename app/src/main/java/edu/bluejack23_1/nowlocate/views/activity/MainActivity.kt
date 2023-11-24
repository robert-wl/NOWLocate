package edu.bluejack23_1.nowlocate.views.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.SharedPreferencesHelper
import edu.bluejack23_1.nowlocate.helpers.StringHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StringHelper.initialize(this.application);
        SharedPreferencesHelper.initialize(this);
        askNotificationPermission()
        IntentHelper.moveTo(this, LoginActivity::class.java)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {

        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, "com.yourapp.permission.POST_NOTIFICATIONS") ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale("com.yourapp.permission.POST_NOTIFICATIONS")) {

            } else {
                requestPermissionLauncher.launch("com.yourapp.permission.POST_NOTIFICATIONS")
            }
        }
    }

}