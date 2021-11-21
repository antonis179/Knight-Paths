package com.example.knight.ui.delegateadapter

import android.os.Parcel
import android.os.Parcelable

/**
 * Basic Data object to be used from adapter delegates
 */
abstract class Data(
    /** the id - to be used from adapter if stable ids are enabled  */
    open val id: Int,

    /**
     * This is used by BaseDelegateDiffAdapter to identify if it should force update the contents of
     * the corresponding delegate
     */
    open val isModified: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        return true
    }

    override fun hashCode() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (isModified) 1 else 0)
    }

    override fun describeContents() = 0

}