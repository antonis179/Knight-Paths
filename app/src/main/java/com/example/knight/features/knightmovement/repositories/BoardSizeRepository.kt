package com.example.knight.features.knightmovement.repositories

import com.example.knight.features.knightmovement.models.BoardSize

interface BoardSizeRepository {
    fun retrieveBoardSizes(): List<BoardSize>
}


class DefaultBoardSizeRepository : BoardSizeRepository {

    override fun retrieveBoardSizes(): List<BoardSize> {
        return mutableListOf<BoardSize>().apply {
            for (i in MIN_BOARD_SIZE..MAX_BOARD_SIZE) add(BoardSize(i, i))
        }
    }

    companion object {
        const val MIN_BOARD_SIZE = 6
        const val MAX_BOARD_SIZE = 16
    }
}