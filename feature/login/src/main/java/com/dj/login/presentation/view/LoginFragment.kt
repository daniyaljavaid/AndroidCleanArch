package com.dj.login.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dj.core.base.BaseFragment
import com.dj.login.databinding.FragmentLoginBinding
import com.dj.login.presentation.state.LoginState
import com.dj.login.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUiEvents(viewModel)
        binding.viewModel = viewModel
        subscribeToObservables()

    }

    private fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Success -> {
                            delay(1500)
//                            val intent = Intent()
//                            intent.setClassName(
//                                this@LoginActivity,
//                                "com.dj.home.presentation.view.HomeActivity"
//                            )
//                            startActivity(intent)
//                            finish()
                        }

                    }
                }
            }
        }
    }


}