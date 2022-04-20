package com.dj.login.presentation.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.dj.core.base.BaseViewModel
import com.dj.core.util.result.ResultState
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.useCase.UseCaseLogin
import com.dj.login.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCaseLogin: UseCaseLogin
) : BaseViewModel() {

    private val _loginState: MutableStateFlow<LoginState> =
        MutableStateFlow(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState

    //perform any task like fetching configurations from server
    //then set _splashLoading to true so splash navigates to this activity
    private val _splashLoading: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    val splashLoading = _splashLoading.asStateFlow()

    val bindingObject = BindingObject()

    init {
        viewModelScope.launch {
            delay(2000)
            _splashLoading.value = false
        }
    }

    fun login(un: String, pass: String) {
        viewModelScope.launch {
            useCaseLogin.login(LoginRequest(un, pass)).onEach { result ->
                when (result) {
                    is ResultState.Success -> {
                        showLoader(false)
                        _loginState.value = LoginState.Success
                        showSnackBar(message = result.message ?: "")
                    }
                    is ResultState.Error -> {
                        showLoader(false)
                        _loginState.value = LoginState.Failure(result.message ?: "Unknown error")
                        showSnackBar(result.message ?: "Unknown error")
                    }
                    is ResultState.Loading -> {
                        showLoader(true)
                    }
                }
            }.launchIn(this)
        }
    }

    class BindingObject {
        val email = ObservableField("")
        val password = ObservableField("")
    }
}