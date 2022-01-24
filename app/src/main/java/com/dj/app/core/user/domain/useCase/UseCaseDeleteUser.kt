package com.dj.app.core.user.domain.useCase

import com.dj.app.core.user.domain.repository.UserRepository
import javax.inject.Inject

class UseCaseDeleteUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(id: String) = userRepository.deleteUser(id)
}