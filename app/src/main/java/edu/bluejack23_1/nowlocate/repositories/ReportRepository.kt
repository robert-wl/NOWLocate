package edu.bluejack23_1.nowlocate.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import edu.bluejack23_1.nowlocate.models.CategoryType
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.models.Report
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ReportRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storageDB = FirebaseStorage.getInstance()

    fun addReport(report: Report){
        db.collection("reports").document(report.id).set(report)
    }

    fun editReport(report: Report){
        db.collection("reports").document(report.id).set(report)
    }

    suspend fun uploadReportImage(uri: Uri?): String? {
        if (uri == null || uri == Uri.EMPTY) {
            return null
        }

        val fileName = UUID.randomUUID().toString() + ".jpg";

        val taskSnapshot = storageDB.reference.child("images/$fileName").putFile(uri).await()

        return taskSnapshot.storage.downloadUrl.await().toString()
    }

    suspend fun getLatestReport(limit: Number, isAscending: Boolean = false): Result<ArrayList<Report>> {
        val direction = if(isAscending) Query.Direction.ASCENDING else Query.Direction.DESCENDING
        val documentReference = db.collection("reports").orderBy("reportDate", direction).limit(limit.toLong())

        return try {
            val querySnapshot = documentReference.get().await()

            if(!querySnapshot.isEmpty){
                val reports = ArrayList<Report>()

                for(document in querySnapshot.documents){
                    val report = document.toObject(Report::class.java)
                    reports.add(report!!)
                }

                Result.success(reports)
            } else {
                Result.failure(Exception("Report not found"))
            }
        } catch (e: Exception){
            Log.wtf("a", e)
            Result.failure(e)
        }
    }

    suspend fun getAllCategory(): Result<ArrayList<Filter>> {
        val documentReference = db.collection("reports")

        return try {
            val filterList = ArrayList<Filter>()

            CategoryType.values().forEach {
                val querySnapshot = documentReference.whereEqualTo("category", it.toString()).get().await()

                if(!querySnapshot.isEmpty){
                    val filter = Filter(it.toString(), querySnapshot.size())
                    filterList.add(filter)
                }
            }

            Result.success(filterList)
        } catch (e: Exception){
            Log.wtf("a", e)
            Result.failure(e)
        }
    }

    suspend fun getReportData(filter: String): Result<ArrayList<Report>> {
        val documentReference = db.collection("reports").whereEqualTo("category", filter)

        return try {
            val querySnapshot = documentReference.get().await()

            if(!querySnapshot.isEmpty){
                val reports = ArrayList<Report>()

                for(document in querySnapshot.documents){
                    val report = document.toObject(Report::class.java)
                    reports.add(report!!)
                }

                Result.success(reports)
            } else {
                Result.failure(Exception("Report not found"))
            }
        } catch (e: Exception){
            Log.wtf("a", e)
            Result.failure(e)
        }
    }

    suspend fun getReportDataSearched(query: String): Result<ArrayList<Report>> {
        val documentReference = db.collection("reports")

        return try {
            val querySnapshot = documentReference.get().await()

            if(!querySnapshot.isEmpty){
                val reports = ArrayList<Report>()

                for(document in querySnapshot.documents){
                    val report = document.toObject(Report::class.java)
                    val name = report?.name
                    if (name != null && name.contains(query)) {
                        reports.add(report)
                    }
                }

                Result.success(reports)
            } else {
                Result.failure(Exception("Report not found"))
            }
        } catch (e: Exception){
            Log.wtf("a", e)
            Result.failure(e)
        }
    }

    fun deleteReport(id: String){
        db.collection("reports").document(id).delete()
    }
}