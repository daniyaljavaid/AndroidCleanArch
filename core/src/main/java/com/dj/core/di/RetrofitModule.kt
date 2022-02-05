package com.dj.core.di

import com.dj.core.util.security.tls.TLS
import com.dj.core.BuildConfig
import com.dj.core.util.interceptor.DecryptionInterceptor
import com.dj.core.util.interceptor.EncryptionInterceptor
import com.dj.core.util.interceptor.HeaderInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        encryptionInterceptor: EncryptionInterceptor,
        decryptionInterceptor: DecryptionInterceptor,
        tls: TLS
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(encryptionInterceptor)
            .addInterceptor(decryptionInterceptor)
            .connectionSpecs(
                listOf(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.CLEARTEXT
                )
            )
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(BuildConfig.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(null)
        if (BuildConfig.IS_TLS_ENABLED) {
            val tlsConfig = tls.getConfig()
            builder.sslSocketFactory(tlsConfig.first, tlsConfig.second)
        }

        return builder.build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}
