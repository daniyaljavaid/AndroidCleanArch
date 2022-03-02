package com.dj.login.presentation.state

sealed class LoginState {
    object Initial : LoginState()
    object Success : LoginState()
    object Failure : LoginState()
}
