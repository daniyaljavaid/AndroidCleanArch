package com.dj.app.core.util.session

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    suspend fun getToken(): String? {
        return "Token"
    }
}

