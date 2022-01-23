package com.dj.app.feature.login.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.dj.app.core.base.BaseViewModel
import com.dj.app.core.util.result.ResultState
import com.dj.app.feature.login.domain.model.LoginRequest
import com.dj.app.feature.login.domain.useCase.UseCaseLogin
import com.dj.app.feature.login.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCaseLogin: UseCaseLogin
) : BaseViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun login(un: String, pass: String) {
        viewModelScope.launch {
            useCaseLogin(LoginRequest(un, pass)).onEach { result ->
                when (result) {
                    is ResultState.Success -> {
                        showLoader(false)
                        _loginState.value = loginState.value.copy(
                            isSuccessful = true
                        )
                        showSnackBar(message = result.message ?: "")
                    }
                    is ResultState.Error -> {
                        showLoader(false)
                        _loginState.value = loginState.value.copy(
                            isSuccessful = false
                        )
                        showSnackBar(result.message ?: "Unknown error")
                    }
                    is ResultState.Loading -> {
                        showLoader(true)
                    }
                }
            }.launchIn(this)
        }
    }
}