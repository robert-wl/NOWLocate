package edu.bluejack23_1.nowlocate.models

import java.lang.reflect.Constructor

data class User (
    val id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    val email: String = "",
    var username: String = "",
    var image: String = "",
    var gender: String = "",
)