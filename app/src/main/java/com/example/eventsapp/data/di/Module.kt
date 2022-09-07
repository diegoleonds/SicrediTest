package com.example.eventsapp.data.di

import com.example.eventsapp.R
import android.content.Context
import com.example.eventsapp.data.endpoint.EventEndpoint
import com.example.eventsapp.data.error.NetworkErrorHandler
import com.example.eventsapp.data.service.EventService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


val dataModule = module {
    single { EventService(provideEventService(get()), get<NetworkErrorHandler>()) }
    factory { NetworkErrorHandler() }
    single{ provideMyRetrofit(androidContext()) }
}

private fun provideMyRetrofit(context: Context): Retrofit {
    val certificateFactory = CertificateFactory.getInstance("X.509")

    val ca = context.resources.openRawResource(R.raw.certificate).use { cert ->
        certificateFactory.generateCertificate(cert)
    }

    val keyStoreType = KeyStore.getDefaultType()
    val keyStore = KeyStore.getInstance(keyStoreType).apply {
        load(null, null)
        setCertificateEntry("ca", ca)
    }

    val trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
    val trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm).apply {
        init(keyStore)
    }

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, trustManagerFactory.trustManagers, null)
    }

    val okHttpClient = OkHttpClient
        .Builder()
        .sslSocketFactory(sslContext.socketFactory)
        .build()

    return Retrofit.Builder()
        .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideEventService(retrofit: Retrofit) = retrofit.create(EventEndpoint::class.java)

