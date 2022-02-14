package com.dj.login.di

import com.dj.login.data.remote.service.LoginService
import com.dj.login.data.repositoryImpl.LoginRepositoryImpl
import com.dj.login.domain.repository.LoginRepository
import com.dj.login.domain.useCase.UseCaseLogin
import com.dj.login.util.LoginValidator
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
    fun provideLoginValidator() = LoginValidator()

    @Provides
    @Singleton
    fun provideUseCaseLogin(loginValidator: LoginValidator, loginRepository: LoginRepository) =
        UseCaseLogin(loginValidator, loginRepository)
}