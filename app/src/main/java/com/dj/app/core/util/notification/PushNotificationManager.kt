package com.dj.app.core.util.notification

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dj.app.R

object PushNotificationManager {
    private const val channelName = "CHANNEL"
    private const val channelId = "999"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelName
            val description = channelName
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description

            val notificationManager = context.getSystemService(
                android.app.NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(
        context: Context,
        title: String?,
        message: String?,
        intent: PendingIntent?,
        id: Int
    ) {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )
                .setPriority(android.app.NotificationManager.IMPORTANCE_HIGH)
        if (intent != null) {
            builder.setContentIntent(intent)
        }
        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(id, builder.build())
    }
}