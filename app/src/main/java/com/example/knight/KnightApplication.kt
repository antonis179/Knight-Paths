package com.example.knight

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

@HiltAndroidApp
class KnightApplication : Application() {

	lateinit var environment: Environment

	init {
		appContext = WeakReference(this)
	}

	override fun onCreate() {
		super.onCreate()
		environment = Environment(this)
	}

	companion object {
		var appContext: WeakReference<Context?> = WeakReference(null)
			private set

		@JvmStatic operator fun get(context: Context): KnightApplication {
			return context.applicationContext as KnightApplication
		}
	}

}
