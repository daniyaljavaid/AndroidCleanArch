package com.dj.login.data.repositoryImpl

import com.dj.core.user.domain.model.User
import com.dj.login.data.remote.service.LoginService
import com.dj.core.util.result.ResultState
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override fun submitLogin(loginRequest: LoginRequest): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading())
        val resp = loginService.login(loginRequest.toDto())
        emit(ResultState.Success(data = resp.user.toUser(), message = resp.message))
    }.catch {
        emit(ResultState.Error("Request Failed"))
    }

}