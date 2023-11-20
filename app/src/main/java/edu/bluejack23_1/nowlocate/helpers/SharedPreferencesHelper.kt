package edu.bluejack23_1.nowlocate.helpers

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper {

    companion object {
        private lateinit var sharedPreferences: SharedPreferences

        fun initialize(context: Context) {
            sharedPreferences = context.getSharedPreferences("NOWLocate", Context.MODE_PRIVATE)
        }

        fun setString(key: String, value: String) {
            sharedPreferences.edit().putString(key, value)?.apply()
        }

        fun getString(key: String): String? {
            return sharedPreferences.getString(key, null)
        }
    }
}