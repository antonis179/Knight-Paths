package com.example.knight.features.knightmovement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BoardSize(
    val width: Int,
    val height: Int
): Parcelable
