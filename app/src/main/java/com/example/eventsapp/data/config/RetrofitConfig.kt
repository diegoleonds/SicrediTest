package com.example.eventsapp.data.config

import android.content.Context
import com.example.eventsapp.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

fun provideMyRetrofit(context: Context): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttp(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkHttp(context: Context): OkHttpClient {
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

    return OkHttpClient
        .Builder()
        .sslSocketFactory(sslContext.socketFactory)
        .build()
}