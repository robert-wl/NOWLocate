package edu.bluejack23_1.nowlocate.interfaces

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface GalleryAccess {
    fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        changeImage.launch(intent)
    }

    val changeImage:  ActivityResultLauncher<Intent>
}