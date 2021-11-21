package com.example.knight.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.knight.KnightApplication


abstract class BaseFragment(@LayoutRes layoutRes: Int): Fragment(layoutRes) {

    // =========================================================================================
    // Getters
    // =========================================================================================

    fun application() = context?.let { KnightApplication[it] }

}