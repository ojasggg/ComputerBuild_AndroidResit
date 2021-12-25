package com.ojash.computerbuild.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
//    @NonNull

    @PrimaryKey
    var _id: String ="",
    var itemType: String?,
    var itemDescription: String?,
    var itemPrice: String?,
    var itemCategory: String?,
    var userID:String?,
    var photo:String?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(itemCategory)
        parcel.writeString(itemType)
        parcel.writeString(itemDescription)
        parcel.writeString(itemPrice)
        parcel.writeString(userID)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}