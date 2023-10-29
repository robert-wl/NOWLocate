package edu.bluejack23_1.nowlocate.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import edu.bluejack23_1.nowlocate.models.User

class UserRepository {
    private val db = FirebaseFirestore.getInstance()

    fun addUser(user: User){
        db.collection("users").document(user.id).set(user)
    }
}