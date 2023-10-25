package edu.bluejack23_1.nowlocate.helper

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ToastHelper {

    companion object {
        fun showMessage(activity: AppCompatActivity, message: String){
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

}