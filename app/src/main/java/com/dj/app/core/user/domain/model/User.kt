package com.dj.app.core.user.domain.model

import com.dj.app.core.user.data.local.UserEntity

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val profilePicture: String,
    val token: String,
    val refreshToken: String
) {
    fun toEntity() = UserEntity(id, fullName, email, profilePicture, token, refreshToken)
}