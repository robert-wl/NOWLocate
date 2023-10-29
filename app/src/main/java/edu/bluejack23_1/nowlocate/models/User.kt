package edu.bluejack23_1.nowlocate.models

import java.lang.reflect.Constructor

data class User (
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val gender: String,
) {
    constructor() : this("", "", "", "", "", "")
}