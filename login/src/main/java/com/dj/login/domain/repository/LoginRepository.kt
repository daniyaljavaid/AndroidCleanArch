package com.dj.login.domain.repository

import com.dj.core.user.domain.model.User
import com.dj.login.domain.model.LoginRequest
import com.dj.core.util.result.ResultState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun submitLogin(loginRequest: LoginRequest): Flow<ResultState<User>>
}