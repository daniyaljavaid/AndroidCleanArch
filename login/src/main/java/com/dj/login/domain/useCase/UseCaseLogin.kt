package com.dj.login.domain.useCase

import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.repository.LoginRepository
import javax.inject.Inject

class UseCaseLogin @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(loginRequest: LoginRequest) = loginRepository.submitLogin(loginRequest)
}