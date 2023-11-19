package edu.bluejack23_1.nowlocate.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.helpers.SharedPreferencesHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesHelper.initialize(this);
        IntentHelper.moveTo(this, LoginActivity::class.java)
    }
}