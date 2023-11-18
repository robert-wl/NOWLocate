package edu.bluejack23_1.nowlocate.models

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class Chat(
    val id: String = "",
    val sender: User = User(),
    val recipient: User = User(),
    val lastMessage: String = "",
    val lastTime: Date = Date()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelable(User::class.java.classLoader) ?: User(),
        parcel.readParcelable(User::class.java.classLoader) ?: User(),
        parcel.readString() ?: "",
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(sender, flags)
        parcel.writeParcelable(recipient, flags)
        parcel.writeString(lastMessage)
        parcel.writeLong(lastTime.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}
