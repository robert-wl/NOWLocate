package edu.bluejack23_1.nowlocate.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, RegisterActivity::class.java)
        finishAndRemoveTask()
        startActivity(intent)

    }
}