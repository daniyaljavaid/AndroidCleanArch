package com.dj.app.core.user.data.remote.service

import com.dj.app.core.user.data.remote.dto.refreshToken.RefreshTokenRequestDto
import com.dj.app.core.user.data.remote.dto.refreshToken.RefreshTokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/refreshToken")
    suspend fun refreshToken(@Body request: RefreshTokenRequestDto): RefreshTokenResponseDto
}