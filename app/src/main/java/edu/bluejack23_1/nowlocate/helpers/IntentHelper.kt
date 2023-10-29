package edu.bluejack23_1.nowlocate.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable

class IntentHelper {

    companion object {

        fun moveTo(activity: Activity, destination: Class<out Any>, disableAnimation: Boolean = false){
            val intent = Intent(activity, destination)
            activity.startActivity(intent)
        }

        fun moveTo(context: Context,  destination: Class<out Any>, disableAnimation: Boolean = false){
            val intent = Intent(context, destination, )
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
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

        fun moveToWithExtraFinish(activity: Activity, destination: Class<out Any>, key: String, value: Parcelable){
            val intent = Intent(activity, destination)
            intent.putExtra(key, value)
            activity.finish()
            activity.startActivity(intent)
        }

        fun moveToWithExtra(context: Context, destination: Class<out Any>, key: String, value: Parcelable){
            val intent = Intent(context, destination)
            intent.putExtra(key, value)
            context.startActivity(intent)
        }
    }

}