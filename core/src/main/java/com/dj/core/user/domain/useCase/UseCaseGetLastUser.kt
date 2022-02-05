package com.dj.core.user.domain.useCase

import com.dj.core.user.domain.repository.UserRepository
import javax.inject.Inject

class UseCaseGetLastUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getLastUser()
}