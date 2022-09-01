package com.example.eventsapp.config

import androidx.multidex.MultiDexApplication
import com.example.eventsapp.data.di.dataModule
import com.example.eventsapp.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : MultiDexApplication() {
    /**
     * Base Application class for the app
     * Start koin for dependency injection
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            modules(uiModule, dataModule)
        }
    }
}