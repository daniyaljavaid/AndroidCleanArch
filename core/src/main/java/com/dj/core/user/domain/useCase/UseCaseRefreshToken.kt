package com.dj.core.user.domain.useCase

import com.dj.core.user.domain.model.RefreshTokenRequest
import com.dj.core.user.domain.repository.UserRepository
import javax.inject.Inject

class UseCaseRefreshToken @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(request: RefreshTokenRequest) = userRepository.refreshToken(request)
}