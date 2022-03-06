package com.dj.login.presentation.state

sealed class LoginState(val message: String? = null) {
    object Initial : LoginState()
    object Success : LoginState()
    class Failure(message: String) : LoginState(message)
}
