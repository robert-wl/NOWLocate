package edu.bluejack23_1.nowlocate.models

import java.sql.Time
import java.util.Date

data class Report(
    val name: String,
    val category: String,
    val shortDescription: String,
    val longDescription: String,
    val lastSeen: String,
    val reportDate: Date
)
