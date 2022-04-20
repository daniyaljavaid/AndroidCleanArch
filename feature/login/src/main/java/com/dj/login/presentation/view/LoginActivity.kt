package com.dj.login.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dj.core.base.BaseActivity
import com.dj.login.databinding.ActivityLoginBinding
import com.dj.login.presentation.state.LoginState
import com.dj.login.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeUiEvents(viewModel)
        binding.viewModel = viewModel
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Success -> {
                            delay(1500)
                            val intent = Intent()
                            intent.setClassName(
                                this@LoginActivity,
                                "com.dj.home.presentation.view.HomeActivity"
                            )
                            startActivity(intent)
                            finish()
                        }

                    }
                }
            }
        }
    }
}