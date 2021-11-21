package com.example.knight.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun View.setBackground(@DrawableRes drawable: Int) {
    background = ContextCompat.getDrawable(context, drawable)
}

fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}