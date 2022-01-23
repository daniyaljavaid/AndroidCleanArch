package com.dj.app.feature.login.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dj.app.core.base.BaseActivity
import com.dj.app.databinding.ActivityLoginBinding
import com.dj.app.feature.login.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        subscribeUiEvents(viewModel)
        subscribeToObservables()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etUserName.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun subscribeToObservables() {
        lifecycleScope.launch {
            viewModel.loginState.collectLatest {
                if (it.isSuccessful) {
                    Timber.e("Successful")
                }
            }
        }
    }
}