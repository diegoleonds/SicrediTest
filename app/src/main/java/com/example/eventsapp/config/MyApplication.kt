package com.example.eventsapp.config

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.example.eventsapp.data.di.dataModule
import com.example.eventsapp.ui.di.uiModule
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext


class MyApplication : MultiDexApplication() {
    /**
     * Base Application class for the app
     * Start koin for dependency injection
     */
    override fun onCreate() {
        super.onCreate()
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            GoogleApiAvailability.getInstance()
                .showErrorNotification(this, e.connectionStatusCode)
        } catch (e: Exception) {
            Log.e("MyApplication", e.message ?: "empty message")
        }
        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            modules(uiModule, dataModule)
        }
    }
}