package edu.bluejack23_1.nowlocate.helpers

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

class SystemHelper {

    companion object {
        fun hideKeyboard(view: View, context: Context) {
            val inputMethodManager =
                ContextCompat.getSystemService(context, InputMethodManager::class.java) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}