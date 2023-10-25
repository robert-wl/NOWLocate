package edu.bluejack23_1.nowlocate.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.bluejack23_1.nowlocate.helper.IntentHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IntentHelper.moveTo(this, CreateReportActivity::class.java)

    }
}