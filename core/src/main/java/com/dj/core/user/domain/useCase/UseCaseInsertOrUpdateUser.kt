package com.dj.core.user.domain.useCase

import com.dj.core.user.domain.model.User
import com.dj.core.user.domain.repository.UserRepository
import javax.inject.Inject

class UseCaseInsertOrUpdateUser @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(user: User) = userRepository.insertOrUpdateUser(user)
}