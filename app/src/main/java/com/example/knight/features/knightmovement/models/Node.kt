package com.example.knight.features.knightmovement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Node(
    val y: Int,
    val x: Int
) : Parcelable
