package edu.bluejack23_1.nowlocate.helper

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class IntentHelper {

    companion object {

        fun moveTo(activity: Activity, destination: Class<out AppCompatActivity>){
            val intent = Intent(activity, destination)
            activity.startActivity(intent)
        }

        fun moveBack(activity: Activity){
            activity.finish()
        }

    }

}