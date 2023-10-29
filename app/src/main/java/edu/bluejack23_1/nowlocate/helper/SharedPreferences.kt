package edu.bluejack23_1.nowlocate.helper

import android.content.Context

class SharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("NOWLocate", Context.MODE_PRIVATE)

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}