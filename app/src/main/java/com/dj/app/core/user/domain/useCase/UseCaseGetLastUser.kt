package com.dj.app.core.user.domain.useCase

import com.dj.app.core.user.domain.repository.UserRepository
import javax.inject.Inject

class UseCaseGetLastUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getLastUser()
}