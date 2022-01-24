package com.dj.app.feature.login.di

import com.dj.app.feature.login.data.remote.service.LoginService
import com.dj.app.feature.login.data.repositoryImpl.LoginRepositoryImpl
import com.dj.app.feature.login.domain.repository.LoginRepository
import com.dj.app.feature.login.domain.useCase.UseCaseLogin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(
        loginService: LoginService
    ): LoginRepository =
        LoginRepositoryImpl(loginService)

    @Provides
    @Singleton
    fun provideUseCaseLogin(loginRepository: LoginRepository) = UseCaseLogin(loginRepository)
}