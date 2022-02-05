package com.dj.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dj.core.BuildConfig
import com.dj.core.user.data.local.UserDao
import com.dj.core.user.data.local.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = BuildConfig.DB_VERSION.toInt()
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}
