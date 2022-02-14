package com.dj.login.domain.model

import com.dj.login.data.remote.dto.LoginRequestDto

data class LoginRequest(val email: String, val password: String) {
    fun toDto() = LoginRequestDto(email, password)
}