package com.dj.core.util.fcm

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object FirebaseUtil {
    suspend fun getToken(): String? = try {
        FirebaseMessaging.getInstance().token.await()
    } catch (exception: Exception) {
        null
    }

}