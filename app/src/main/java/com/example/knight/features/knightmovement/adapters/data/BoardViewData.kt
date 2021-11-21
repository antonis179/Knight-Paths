package com.example.knight.features.knightmovement.adapters.data

import android.os.Parcelable
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.models.BoardSize
import com.example.knight.features.knightmovement.models.Node
import com.example.knight.features.knightmovement.models.Path
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class BoardViewData(
    val size: BoardSize,
    var sourceNode: Node?,
    var destinationNode: Node?,
    val highlightPath: Path?,
    override val id: Int,
    override val isModified: Boolean = false
) : Data(id, isModified), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (!super.equals(other)) return false

        val converted = other as BoardViewData
        if (size != converted.size) return false
        if (highlightPath != converted.highlightPath) return false
        return true
    }

    override fun hashCode() = Objects.hash(super.hashCode(), size, highlightPath)
}
