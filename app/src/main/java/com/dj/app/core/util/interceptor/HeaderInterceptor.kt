package com.dj.app.core.util.interceptor

import com.dj.app.core.util.session.SessionManager
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(private val sessionManager: SessionManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()

        runBlocking {
            if (shouldAttachToken(request.url.toString())) {
                sessionManager.getToken()?.let {
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }
            }
            request = requestBuilder.build()
        }
        return chain.proceed(request)
    }

    private fun shouldAttachToken(url: String) =
        !url.contains("refreshtoken", true) && !url.contains("login", true)

}