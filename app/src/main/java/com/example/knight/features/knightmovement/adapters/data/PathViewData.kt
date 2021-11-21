package com.example.knight.features.knightmovement.adapters.data

import android.os.Parcelable
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.features.knightmovement.models.Path
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class PathViewData(
    val path: Path,
    override val id: Int,
    override val isModified: Boolean = false
) : Data(id, isModified), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PathViewData) return false
        if (!super.equals(other)) return false

        if (path != other.path) return false

        return true
    }

    override fun hashCode() = Objects.hash(path)

}
