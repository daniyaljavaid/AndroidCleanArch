package com.dj.app.feature.login.data.repositoryImpl

import com.dj.core.user.domain.model.User
import com.dj.app.feature.login.data.remote.service.LoginService
import com.dj.app.feature.login.domain.model.LoginRequest
import com.dj.app.feature.login.domain.repository.LoginRepository
import com.dj.core.util.result.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override fun submitLogin(loginRequest: LoginRequest): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading())
        val resp = loginService.login(loginRequest.toDto())
        emit(ResultState.Success(data = resp.user.toUser(), message = resp.message))
    }

}