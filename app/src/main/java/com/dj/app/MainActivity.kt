package com.dj.app

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dj.app.databinding.ActivityMainBinding
import com.dj.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //perform any task like fetching configurations from server
        //then set value to true so splash navigates to this activity
//        splashScreen.setKeepOnScreenCondition {
//            true
//        }
        
        setContentView(binding.root)
    }
}