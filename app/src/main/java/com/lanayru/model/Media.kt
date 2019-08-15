package com.lanayru.model

import android.os.Parcel
import android.os.Parcelable

data class Media (
        val id: Long,
        var path: String,
        var thumb: String,
        var name: String,
        var mediaType: Int = 0,
        var time: Long = 0,
        var size: Long,
        var duration: Long = -1
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(path)
        parcel.writeString(thumb)
        parcel.writeString(name)
        parcel.writeInt(mediaType)
        parcel.writeLong(time)
        parcel.writeLong(size)
        parcel.writeLong(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Media> {
        override fun createFromParcel(parcel: Parcel): Media {
            return Media(parcel)
        }

        override fun newArray(size: Int): Array<Media?> {
            return arrayOfNulls(size)
        }
    }
}