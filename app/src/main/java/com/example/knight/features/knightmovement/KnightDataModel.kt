package com.example.knight.features.knightmovement

import android.os.Parcelable
import com.example.knight.features.knightmovement.models.BoardSize
import com.example.knight.features.knightmovement.models.Node
import com.example.knight.features.knightmovement.models.Path
import kotlinx.parcelize.Parcelize

@Parcelize
class KnightDataModel(
    var sourceNode: Node? = null,
    var destinationNode: Node? = null,
    val boardSizes: MutableList<BoardSize> = mutableListOf(),
    var maxMoves: Int,
    val paths: MutableList<Path> = mutableListOf(),
    var selectedBoardSize: BoardSize? = null,
    var highlightPath: Path? = null
) : Parcelable {

    fun cloneWithoutPaths() = KnightDataModel(
        sourceNode,
        destinationNode,
        boardSizes,
        maxMoves,
        mutableListOf(),
        selectedBoardSize,
        highlightPath
    )

    fun clearNodes() {
        sourceNode = null
        destinationNode = null
    }
}