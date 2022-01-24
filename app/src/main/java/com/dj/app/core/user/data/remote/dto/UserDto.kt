package com.dj.app.core.user.data.remote.dto

import com.dj.app.core.user.data.local.UserEntity

data class UserDto(
    val id: String,
    val fullName: String,
    val email: String,
    val profilePicture: String,
    val token: String,
    val refreshToken: String
) {
    fun toUserEntity() = UserEntity(id, fullName, email, profilePicture, token, refreshToken)
}