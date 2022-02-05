package com.dj.home.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dj.core.base.BaseActivity
import com.dj.home.R
import com.dj.home.databinding.ActivityHomeBinding
import com.dj.home.pages.Fragment1
import com.dj.home.pages.Fragment2
import com.dj.home.pages.Fragment3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(Fragment1())
                    true
                }
                R.id.settings -> {
                    loadFragment(Fragment2())
                    true
                }

                R.id.notifications -> {
                    loadFragment(Fragment3())
                    true
                }
                else -> false
            }
        }
        loadFragment(Fragment1())

    }

    override fun onBackPressed() {
        if (binding.bottomNav.selectedItemId == R.id.home) {
            super.onBackPressed()
        } else {
            binding.bottomNav.selectedItemId = R.id.home
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}