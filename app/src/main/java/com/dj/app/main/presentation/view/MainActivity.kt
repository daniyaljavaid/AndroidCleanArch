package com.dj.app.main.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dj.app.databinding.ActivityMainBinding
import com.dj.app.main.presentation.viewmodel.MainViewModel
import com.dj.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //perform any task like fetching configurations from server
        //then set value to false so splash navigates to this activity
        splashScreen.setKeepOnScreenCondition {
            viewModel.endSplash.value
        }
    }
}