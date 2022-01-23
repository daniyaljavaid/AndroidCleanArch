package com.dj.app.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dj.app.BuildConfig

@Database(
    entities = [], version = BuildConfig.DB_VERSION.toInt()
)

abstract class AppDatabase : RoomDatabase() {

}
