package com.dj.core.util.fcm

import android.content.Intent
import com.dj.core.util.notification.PushNotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.random.Random

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Timber.e("Token received -  $token")
    }

    override fun handleIntent(intent: Intent) {
        PushNotificationManager.showNotification(
            this@FCMService,
            intent.extras?.getString("gcm.notification.title") ?: return,
            intent.extras?.getString("gcm.notification.body") ?: return,
            null,
            Random.nextInt(999999999)
        )
    }
}