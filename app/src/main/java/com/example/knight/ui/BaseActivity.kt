package com.example.knight.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.knight.KnightApplication

abstract class BaseActivity : AppCompatActivity() {

    // =========================================================================================
    // Getters
    // =========================================================================================

    fun application(): KnightApplication {
        return KnightApplication[this]
    }

}
