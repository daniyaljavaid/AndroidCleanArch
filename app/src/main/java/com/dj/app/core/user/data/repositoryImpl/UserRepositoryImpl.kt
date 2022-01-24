package com.dj.app.core.user.data.repositoryImpl

import com.dj.app.core.user.data.local.UserDao
import com.dj.app.core.user.data.remote.service.UserService
import com.dj.app.core.user.domain.model.User
import com.dj.app.core.user.domain.repository.UserRepository
import com.dj.app.core.util.result.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) : UserRepository {

    override fun insertOrUpdateUser(user: User): Flow<ResultState<Boolean>> = flow {
        userDao.insert(user.toEntity())
        emit(ResultState.Success(data = true))
    }

    override fun getUser(id: String): Flow<ResultState<User>> = flow {
        val user = userDao.getUser(id)
        emit(ResultState.Success(data = user.toUser()))
    }

    override fun deleteUser(id: String): Flow<ResultState<Boolean>> = flow {
        userDao.deleteUser(id)
        emit(ResultState.Success(data = true))
    }

}