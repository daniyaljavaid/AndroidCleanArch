package com.dj.app.core.user.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dj.app.core.user.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val email: String,
    val profilePicture: String,
    val token: String,
    val refreshToken: String,
    val tokenExpiryTime: Long,
    val lastTokenRefreshTime: Long = 0
) {
    fun toUser() = User(
        id,
        fullName,
        email,
        profilePicture,
        token,
        refreshToken,
        tokenExpiryTime,
        lastTokenRefreshTime
    )
}