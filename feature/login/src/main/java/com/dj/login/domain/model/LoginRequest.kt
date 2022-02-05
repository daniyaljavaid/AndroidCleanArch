package com.dj.login.domain.model

import com.dj.login.data.remote.dto.LoginRequestDto

data class LoginRequest(val username: String, val password: String) {
    fun toDto() = LoginRequestDto(username, password)
}