package com.dj.login.domain.useCase

import com.dj.core.user.domain.model.User
import com.dj.core.util.result.ResultState
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.repository.LoginRepository
import com.dj.login.util.LoginValidator
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseLogin @Inject constructor(
    private val validator: LoginValidator,
    private val loginRepository: LoginRepository
) {
    operator fun invoke(loginRequest: LoginRequest) = flow<ResultState<User>> {
        if (!validator.isEmailValid(loginRequest.username)) {
            emit(ResultState.Error("Invalid Email"))
        } else if (!validator.isPasswordValid(loginRequest.password)) {
            emit(ResultState.Error("Invalid Password"))
        } else
            emitAll(loginRepository.submitLogin(loginRequest))
    }

}