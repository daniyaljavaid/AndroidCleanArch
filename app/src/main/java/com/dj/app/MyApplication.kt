package com.dj.app

import android.app.Application
import com.dj.app.core.util.notification.PushNotificationManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        PushNotificationManager.createNotificationChannel(this)
    }
}