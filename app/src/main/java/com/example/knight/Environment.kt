package com.example.knight

import android.content.Context
import timber.log.Timber

class Environment (
    val context: Context
) {

    init {
        initLog()
    }

    private fun initLog() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

}
