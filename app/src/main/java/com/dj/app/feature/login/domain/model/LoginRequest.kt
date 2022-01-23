package com.dj.app.feature.login.domain.model

import com.dj.app.feature.login.data.remote.dto.LoginRequestDto

data class LoginRequest(val username: String, val password: String) {
    fun toDto() = LoginRequestDto(username, password)
}