package com.example.knight.features.knightmovement.adapters

import com.example.knight.ui.delegateadapter.Data
import com.example.knight.ui.delegateadapter.DelegateDiffAdapter
import com.example.knight.features.knightmovement.adapters.delegates.*
import com.example.knight.features.knightmovement.models.Node
import com.example.knight.features.knightmovement.models.Path

class KnightAdapter(
    boardSizeCallback: (selectedBoard: Int) -> Unit,
    maxMovesCallback: (maxMoves: Int) -> Unit,
    boardCallback: (sourceNode: Node?, destinationNode: Node?) -> Unit,
    onPathSelected: (path: Path) -> Unit
) : DelegateDiffAdapter<Data>(null) {

    init {
        TitleDelegate().also { addDelegate(it.viewType, it.make()) }
        UiMessageDelegate().also { addDelegate(it.viewType, it.make()) }
        BoardSizeDelegate(boardSizeCallback).also { addDelegate(it.viewType, it.make()) }
        MaxMovesDelegate(maxMovesCallback).also { addDelegate(it.viewType, it.make()) }
        BoardViewDelegate(boardCallback).also { addDelegate(it.viewType, it.make()) }
        PathViewDelegate(onPathSelected).also { addDelegate(it.viewType, it.make()) }
    }

}

