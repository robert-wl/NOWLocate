package edu.bluejack23_1.nowlocate.models

data class Chat(
    val id: String = "",
    val person1: String = "",
    val person2: String = "",
    val messages: ArrayList<Message> = ArrayList()
)
