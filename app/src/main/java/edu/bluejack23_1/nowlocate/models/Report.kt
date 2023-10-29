package edu.bluejack23_1.nowlocate.models

import android.os.Parcel
import android.os.Parcelable
import java.sql.Time
import java.util.Date

data class Report(
    var id: String,
    var name: String,
    var image: String,
    var category: String,
    var shortDescription: String,
    var longDescription: String,
    var lastSeen: String,
    var reportDate: Date,
    var reportedBy: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        Date(parcel.readLong()),
        parcel.readString() ?: "",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(category)
        parcel.writeString(shortDescription)
        parcel.writeString(longDescription)
        parcel.writeString(lastSeen)
        parcel.writeLong(reportDate.time)
        parcel.writeString(reportedBy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Report> {
        override fun createFromParcel(parcel: Parcel): Report {
            return Report(parcel)
        }

        override fun newArray(size: Int): Array<Report?> {
            return arrayOfNulls(size)
        }
    }

}
