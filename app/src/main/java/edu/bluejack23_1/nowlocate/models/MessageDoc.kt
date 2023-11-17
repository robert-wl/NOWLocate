package edu.bluejack23_1.nowlocate.models

import java.util.Date

data class MessageDoc(
    val sender: String = "",
    val message: String = "",
    val sentAt: Date = Date()

)
