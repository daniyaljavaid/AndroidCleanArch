package com.dj.login.domain.useCase

import com.dj.core.user.domain.model.User
import com.dj.core.user.domain.useCase.UseCaseGetLastUser
import com.dj.core.user.domain.useCase.UseCaseInsertOrUpdateUser
import com.dj.core.util.result.ResultState
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.repository.LoginRepository
import com.dj.login.util.LoginValidator
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class UseCaseLogin @Inject constructor(
    private val validator: LoginValidator,
    private val loginRepository: LoginRepository,
    private val useCaseInsertOrUpdateUser: UseCaseInsertOrUpdateUser,
    private val useCaseGetLastUser: UseCaseGetLastUser
) {
    operator fun invoke(loginRequest: LoginRequest) = flow<ResultState<User>> {
        if (!validator.isEmailValid(loginRequest.email)) {
            emit(ResultState.Error("Invalid Email"))
        } else if (!validator.isPasswordValid(loginRequest.password)) {
            emit(ResultState.Error("Invalid Password"))
        } else {
            loginRepository.submitLogin(loginRequest).collect {
                emit(it)
                if (it is ResultState.Success) {
                    it.data?.let { user ->
                        useCaseGetLastUser().collect { lastUser ->
                            if (lastUser is ResultState.Success)
                                Timber.e(lastUser.data?.id ?: "No user exits")
                        }
                        useCaseInsertOrUpdateUser(user).collect()
                    }
                }
            }
        }
    }

}