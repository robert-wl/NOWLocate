package edu.bluejack23_1.nowlocate.helper

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

class IntentHelper {

    companion object {

        fun moveTo(activity: Activity, destination: Class<out Any>){
            val intent = Intent(activity, destination)
            activity.startActivity(intent)
        }

        fun moveToFinish(activity: Activity, destination: Class<out Any>){
            val intent = Intent(activity, destination)
            activity.startActivity(intent)
            activity.finish()
        }

        fun moveBack(activity: Activity){
            activity.finish()
        }

        fun moveToWithExtra(activity: Activity, destination: Class<out Any>, key: String, value: String){
            val intent = Intent(activity, destination)
            intent.putExtra(key, value)
            activity.startActivity(intent)
        }

        fun moveToWithExtra(activity: Activity, destination: Class<out Any>, key: String, value: Parcelable){
            val intent = Intent(activity, destination)
            intent.putExtra(key, value)
            activity.startActivity(intent)
        }
    }

}