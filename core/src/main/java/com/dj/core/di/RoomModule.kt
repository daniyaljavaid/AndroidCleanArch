package com.dj.core.di

import android.content.Context
import androidx.room.Room
import com.dj.core.BuildConfig
import com.dj.core.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, BuildConfig.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
}