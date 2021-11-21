package com.example.knight.features.home

import android.os.Bundle
import androidx.fragment.app.commitNow
import com.example.knight.R
import com.example.knight.databinding.ActivityHomeBinding
import com.example.knight.ui.BaseActivity
import com.example.knight.features.knightmovement.KnightFragment
import com.example.knight.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigateToMain()
        }

    }



    private fun navigateToMain() {
        supportFragmentManager.commitNow {
            replace(R.id.container, KnightFragment.newInstance())
        }
    }


}