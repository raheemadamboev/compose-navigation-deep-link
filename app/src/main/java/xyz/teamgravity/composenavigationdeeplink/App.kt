package xyz.teamgravity.composenavigationdeeplink

import android.app.Application
import android.app.NotificationManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DeepLinkNotification(
            application = this,
            manager = getSystemService(NotificationManager::class.java)
        ).show()
    }
}