package com.dj.core.util.interceptor

import android.os.Build
import androidx.annotation.RequiresApi
import com.dj.core.util.security.rds.SecurityManagerRDS
import com.dj.core.BuildConfig
import com.dj.core.util.extension.toRequestString
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class EncryptionInterceptor @Inject constructor(private val securityManager: SecurityManagerRDS) :
    Interceptor {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (BuildConfig.IS_ENCRYPTION_ENABLED && request.method != "GET") {
            val rawBody = request.body
            var encryptedBody = ""
            val mediaType: MediaType = "text/plain; charset=utf-8".toMediaType()
            try {
                rawBody?.let { body ->
                    val rawBodyString: String? = body.toRequestString()
                    rawBodyString?.let { stringBody ->
                        Timber.e("Request Body %s", stringBody)
                        encryptedBody = securityManager.encrypt(stringBody)
                        Timber.e("Encrypted Request Body %s", encryptedBody)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body: RequestBody = encryptedBody.toRequestBody(mediaType)

            request = request.newBuilder()
                .header("Content-Type", body.contentType().toString())
                .header("Content-Length", body.contentLength().toString())
                .method(request.method, body).build()

        }

        return chain.proceed(request)
    }
}
//https://medium.com/swlh/okhttp-interceptors-with-retrofit-2dcc322cc3f3