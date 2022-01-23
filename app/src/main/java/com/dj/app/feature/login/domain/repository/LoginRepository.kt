package com.dj.app.feature.login.domain.repository

import com.dj.app.feature.login.domain.model.LoginRequest
import com.dj.app.core.util.result.ResultState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun submitLogin(loginRequest: LoginRequest): Flow<ResultState<String>>
}