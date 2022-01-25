package com.dj.app.core.util.session

import com.dj.app.core.user.domain.model.RefreshTokenRequest
import com.dj.app.core.user.domain.model.User
import com.dj.app.core.user.domain.useCase.UseCaseGetLastUser
import com.dj.app.core.user.domain.useCase.UseCaseGetUser
import com.dj.app.core.user.domain.useCase.UseCaseRefreshToken
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val useCaseGetLastUser: Lazy<UseCaseGetLastUser>,
    private val useCaseRefreshToken: Lazy<UseCaseRefreshToken>
) {

    suspend fun getToken(): String? {
        val userResult = useCaseGetLastUser.get().invoke().firstOrNull()
        val user = userResult?.data ?: return null
        if (isTokenExpired(user)) {
            val updatedToken = useCaseRefreshToken.get()
                .invoke(RefreshTokenRequest(user.token, user.refreshToken)).firstOrNull()
            return updatedToken?.data
        }
        return null
    }

    private fun isTokenExpired(user: User) =
        ((user.lastTokenRefreshTime) + (user.tokenExpiryTime)) <= Calendar.getInstance().time.time
}

