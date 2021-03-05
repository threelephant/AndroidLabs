package com.example.lab05

import android.os.Parcel
import android.os.Parcelable

class Item() : Parcelable {

    var id: Long = 0
    var kind: String = ""
    var title: String = ""
    var price: Double = 0.0
    var weight: Double = 0.0
    var yearCreation: Int = 0
    var photo: String = ""

    val info: String
        get() = "$kind $title ($price ₽)"

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        kind = parcel.readString() ?: ""
        title = parcel.readString() ?: ""
        price = parcel.readDouble()
        weight = parcel.readDouble()
        yearCreation = parcel.readInt()
        photo = parcel.readString() ?: ""
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if (dest !== null) {
            dest.writeLong(id)
            dest.writeString(kind)
            dest.writeString(title)
            dest.writeDouble(price)
            dest.writeDouble(weight)
            dest.writeInt(yearCreation)
            dest.writeString(photo)
        }
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