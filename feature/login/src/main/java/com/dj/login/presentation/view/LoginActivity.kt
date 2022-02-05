package com.dj.login.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dj.core.base.BaseActivity
import com.dj.login.databinding.ActivityLoginBinding
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
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeUiEvents(viewModel)
        binding.viewModel = viewModel
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        lifecycleScope.launch {
            viewModel.loginState.collectLatest {
                if (it.isSuccessful) {
                    Timber.e("Successful")
                    delay(1500)
                    val intent = Intent()
                    intent.setClassName(this@LoginActivity, "com.dj.home.presentation.view.HomeActivity")
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}