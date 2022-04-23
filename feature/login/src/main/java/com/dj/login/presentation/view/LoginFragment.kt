package com.dj.login.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dj.core.base.BaseFragment
import com.dj.login.R
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
                            //implicit deeplink
                            val request = NavDeepLinkRequest.Builder
                                .fromUri("my-app://feature.home/home_fragment".toUri())
                                .build()
                            val options =
                                NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()

                            findNavController().navigate(request, options)
                        }

                    }
                }
            }
        }
    }


}