package edu.bluejack23_1.nowlocate.handlers

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.helpers.IntentHelper
import edu.bluejack23_1.nowlocate.views.activity.ConversationActivity
import edu.bluejack23_1.nowlocate.views.activity.HomeActivity
import edu.bluejack23_1.nowlocate.views.activity.ProfileActivity

class BottomNavigationViewHandler(
    private val context: Context,
    private val bottomNavigationView: BottomNavigationView
) {
    companion object {
        private var itemNumber: Number = R.id.home
        fun moveTo(context: Context, destination: Class<out Any>, number: Number){
            itemNumber = number
            val intent = Intent(context, destination)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }
    }

    private val onNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home -> {
                moveTo(context, HomeActivity::class.java, R.id.home)

                true
            }
            R.id.chat -> {
                moveTo(context, ConversationActivity::class.java, R.id.chat)

                true
            }
            R.id.profile -> {
                moveTo(context, ProfileActivity::class.java, R.id.profile)

                true
            }
            else -> false
        }
    }

    init {
        bottomNavigationView.menu.findItem(itemNumber.toInt()).isChecked = true
        bottomNavigationView.setOnItemSelectedListener(onNavigationSelectedListener)
    }
}