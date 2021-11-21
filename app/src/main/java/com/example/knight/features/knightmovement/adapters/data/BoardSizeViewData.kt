package com.example.knight.features.knightmovement.adapters.data

import android.os.Parcelable
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.models.BoardSize
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class BoardSizeViewData(
    val options: List<BoardSize>,
    val selectedItem: Int,
    override val id: Int,
    override val isModified: Boolean = false
) : Data(id, isModified), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (!super.equals(other)) return false

        val converted = other as BoardSizeViewData
        if (options != converted.options) return false
        if (selectedItem != converted.selectedItem) return false
        return true
    }

    override fun hashCode() = Objects.hash(super.hashCode(), options, selectedItem)
}
