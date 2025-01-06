package xyz.teamgravity.composenavigationdeeplink

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri

class DeepLinkNotification(
    private val application: Application,
    private val manager: NotificationManager
) {

    private companion object {
        const val CHANNEL_ID = "xyz.teamgravity.composenavigationdeeplink.DeepLinkNotification"
        const val CHANNEL_NAME = "Deep Link"
        const val NOTIFICATION_ID = 1
    }

    private fun getActivity(): PendingIntent {
        val intent = Intent(application, MainActivity::class.java)
        intent.data = "https://${DeepLinkConst.DOMAIN}/77".toUri()

        val builder = TaskStackBuilder.create(application)
        builder.addNextIntentWithParentStack(intent)
        return builder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun createChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val builder = NotificationCompat.Builder(application, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(application.resources, R.drawable.ic_launcher_foreground))
            .setContentIntent(getActivity())
            .setContentTitle(application.getString(R.string.app_launched))
            .setContentText(application.getString(R.string.tap_to_open_deep_link))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        return NotificationCompat.BigTextStyle(builder)
            .setBigContentTitle(application.getString(R.string.app_launched))
            .bigText(application.getString(R.string.tap_to_open_deep_link))
            .build() ?: builder.build()
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun show() {
        if (manager.areNotificationsEnabled()) {
            createChannel()
            manager.notify(NOTIFICATION_ID, createNotification())
        }
    }
}