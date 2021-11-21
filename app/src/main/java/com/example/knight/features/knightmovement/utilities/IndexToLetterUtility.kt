package com.example.knight.features.knightmovement.utilities


object IndexToLetterUtility {

    const val DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val COLUMN_BASE = 26 //alphabet size

    fun indexToLetter(index: Int): String {
        if (index <= 0)
            throw Exception("index must be a positive number")

        if (index <= COLUMN_BASE)
            return DIGITS[index - 1].toString()

        StringBuilder().apply {
            var current = index
            while (current > 0) {
                append(DIGITS[--current % COLUMN_BASE])
                current /= COLUMN_BASE
            }

            return reverse().toString()
        }
    }


}