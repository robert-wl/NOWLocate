package edu.bluejack23_1.nowlocate.models

import java.util.Date

data class MessageDoc(
    var id: String = "",
    val sender: String = "",
    val message: String = "",
    val sentAt: Date = Date()

)
