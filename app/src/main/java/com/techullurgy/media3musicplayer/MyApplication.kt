package com.techullurgy.media3musicplayer

import android.app.Application
import com.techullurgy.media3musicplayer.di.appModule
import com.techullurgy.media3musicplayer.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule, dataModule)
        }
    }
}