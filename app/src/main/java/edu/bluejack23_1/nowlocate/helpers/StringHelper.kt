package edu.bluejack23_1.nowlocate.helpers

import android.app.Application

class StringHelper {
    companion object {
        private lateinit var application: Application

        fun initialize(application: Application) {
            this.application = application
        }
        fun getString(id: Int): String? {
            return application.getString(id)
        }
    }
}