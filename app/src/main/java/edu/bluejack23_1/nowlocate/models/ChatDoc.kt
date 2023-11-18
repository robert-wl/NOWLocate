package edu.bluejack23_1.nowlocate.models

import java.util.Date

data class ChatDoc(

    val id: String = "",
    val person1: String = "",
    val person2: String = "",
    val lastMessage: String = "",
    val lastTime: Date = Date()

)
