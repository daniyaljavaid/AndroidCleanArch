package com.dj.core.user.data.repositoryImpl

import com.dj.core.user.data.local.UserDao
import com.dj.core.user.data.remote.service.UserService
import com.dj.core.user.domain.model.RefreshTokenRequest
import com.dj.core.user.domain.model.User
import com.dj.core.user.domain.repository.UserRepository
import com.dj.core.util.result.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) : UserRepository {

    override fun insertOrUpdateUser(user: User): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading())
        userDao.insert(user.toEntity())
        emit(ResultState.Success(data = true))
    }.catch { exception ->
        Timber.e(exception.message)
        emit(ResultState.Error("User insert/update failed"))
    }

    override fun getUser(id: String): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading())
        val user = userDao.getUser(id)
        emit(ResultState.Success(data = user.toUser()))
    }.catch { exception ->
        Timber.e(exception.message)
        emit(ResultState.Error("Get user failed"))
    }

    override fun getLastUser(): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading())
        val user = userDao.getLastUser()
        emit(ResultState.Success(data = user?.toUser()))
    }.catch { exception ->
        Timber.e(exception.message)
        emit(ResultState.Error("Get last user failed"))
    }

    override fun deleteUser(id: String): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading())
        userDao.deleteUser(id)
        emit(ResultState.Success(data = true))
    }.catch { exception ->
        Timber.e(exception.message)
        emit(ResultState.Error("Delete user failed"))
    }

    override fun refreshToken(request: RefreshTokenRequest): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading())
        val resp = userService.refreshToken(request.toDto())
        emit(ResultState.Success(data = resp.refreshToken))
    }.catch { exception ->
        Timber.e(exception.message)
        emit(ResultState.Error("Refresh token failed"))
    }

}