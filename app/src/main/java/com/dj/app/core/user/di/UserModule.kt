package com.dj.app.core.user.di

import com.dj.app.core.db.AppDatabase
import com.dj.app.core.user.data.remote.service.UserService
import com.dj.app.core.user.data.repositoryImpl.UserRepositoryImpl
import com.dj.app.core.user.domain.repository.UserRepository
import com.dj.app.core.user.domain.useCase.UseCaseDeleteUser
import com.dj.app.core.user.domain.useCase.UseCaseGetUser
import com.dj.app.core.user.domain.useCase.UseCaseInsertOrUpdateUser
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
class UserModule {

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService,
        appDatabase: AppDatabase
    ): UserRepository =
        UserRepositoryImpl(userService, appDatabase.userDao())

    @Provides
    @Singleton
    fun provideUseCaseDeleteUser(userRepository: UserRepository) = UseCaseDeleteUser(userRepository)

    @Provides
    @Singleton
    fun provideUseCaseGetUser(userRepository: UserRepository) = UseCaseGetUser(userRepository)

    @Provides
    @Singleton
    fun provideUseCaseInsertOrUpdateUser(userRepository: UserRepository) =
        UseCaseInsertOrUpdateUser(userRepository)
}