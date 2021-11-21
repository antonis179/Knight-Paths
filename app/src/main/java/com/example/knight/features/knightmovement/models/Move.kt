package com.example.knight.features.knightmovement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Move(
    val nodes: List<Node>
): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Move) return false

        if (nodes != other.nodes) return false

        return true
    }

    override fun hashCode() = Objects.hash(nodes)
}