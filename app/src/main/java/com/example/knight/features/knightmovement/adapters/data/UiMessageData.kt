package com.example.knight.features.knightmovement.adapters.data

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.knight.ui.delegateadapter.Data
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class UiMessageData(
    @StringRes val message: Int,
    override val id: Int,
    override val isModified: Boolean = false
) : Data(id, isModified), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (!super.equals(other)) return false

        val converted = other as UiMessageData
        if (message != converted.message) return false
        return true
    }

    override fun hashCode() = Objects.hash(super.hashCode(), message)
}
