package edu.bluejack23_1.nowlocate.models

import java.util.Date

data class Chat(

    val id: String = "",
    val sender: User = User(),
    val recipient: User = User(),
    val messages: ArrayList<Message> = ArrayList(),
    val lastMessage: String = "",
    val lastTime: Date = Date()

)
