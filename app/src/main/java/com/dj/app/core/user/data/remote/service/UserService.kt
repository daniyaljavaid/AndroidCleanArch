package com.dj.app.core.user.data.remote.service

import com.dj.app.feature.login.data.remote.dto.LoginRequestDto
import com.dj.app.feature.login.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/refreshToken")
    suspend fun refreshToken(@Body request: LoginRequestDto): LoginResponseDto
}