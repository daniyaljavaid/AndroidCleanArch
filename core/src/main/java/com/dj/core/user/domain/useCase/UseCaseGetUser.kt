package com.dj.core.user.domain.useCase

import com.dj.core.user.domain.repository.UserRepository
import javax.inject.Inject

class UseCaseGetUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(id: String) = userRepository.getUser(id)
}