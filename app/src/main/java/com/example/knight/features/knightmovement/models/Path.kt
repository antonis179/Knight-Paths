package com.example.knight.features.knightmovement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Path(
    val moves: List<Move>
): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Path) return false

        if (moves != other.moves) return false

        return true
    }

    override fun hashCode() = Objects.hash(moves)

}