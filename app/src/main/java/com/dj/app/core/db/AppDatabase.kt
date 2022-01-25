package com.dj.app.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dj.app.BuildConfig
import com.dj.app.core.user.data.local.UserDao
import com.dj.app.core.user.data.local.UserEntity
import com.dj.app.core.user.domain.model.User

@Database(
    entities = [
        UserEntity::class
    ], version = BuildConfig.DB_VERSION.toInt()
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}
