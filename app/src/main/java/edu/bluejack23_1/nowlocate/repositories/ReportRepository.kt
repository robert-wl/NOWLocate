package edu.bluejack23_1.nowlocate.repositories

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import edu.bluejack23_1.nowlocate.models.Report
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ReportRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storageDB = FirebaseStorage.getInstance()

    fun addReport(report: Report){
        db.collection("reports").document(report.id).set(report)
    }

    suspend fun uploadReportImage(uri: Uri?): String? {
        if (uri == null) {
            return null
        }

        val fileName = UUID.randomUUID().toString() + ".jpg";

        val taskSnapshot = storageDB.reference.child("images/$fileName").putFile(uri).await()

        return taskSnapshot.storage.downloadUrl.await().toString()
    }
}