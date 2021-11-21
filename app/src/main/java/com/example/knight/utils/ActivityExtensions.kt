package com.example.knight.utils

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Activity extension function used to create ViewBinding object for an activity
 *
 * Example of usage :
 *
 *  class MainActivity : AppCompatActivity() {
        private val binding by viewBinding(MainActivityBinding::inflate)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)

            binding.button.onClick {
            showToast("hello world!")
        }
        ...

 */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }