package dev.horizon.android

import android.app.Application
import android.content.Context
import dev.horizon.android.i18n.LauncherText

class HorizonApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        LauncherText.attach(base)
    }

    override fun onCreate() {
        super.onCreate()
        LauncherText.attach(this)
    }
}
