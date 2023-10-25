package edu.bluejack23_1.nowlocate.models

import java.sql.Time

data class Report(
    val name: String,
    val category: String,
    val shortDescription: String,
    val description: String,
    val lastSeen: String,
)
