package com.dj.app.feature.login.data.remote.service

import com.dj.app.feature.login.data.remote.dto.LoginRequestDto
import com.dj.app.feature.login.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}