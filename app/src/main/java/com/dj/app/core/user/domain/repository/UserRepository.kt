package com.dj.app.core.user.domain.repository

import com.dj.app.core.user.domain.model.RefreshTokenRequest
import com.dj.app.core.user.domain.model.User
import com.dj.app.core.util.result.ResultState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun insertOrUpdateUser(user: User): Flow<ResultState<Boolean>>

    fun getUser(id: String): Flow<ResultState<User>>

    fun getLastUser(): Flow<ResultState<User>>

    fun deleteUser(id: String): Flow<ResultState<Boolean>>

    fun refreshToken(request: RefreshTokenRequest): Flow<ResultState<String>>

}