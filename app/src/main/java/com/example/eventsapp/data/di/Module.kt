package com.example.eventsapp.data.di

import com.example.eventsapp.data.config.provideMyRetrofit
import com.example.eventsapp.data.endpoint.EventEndpoint
import com.example.eventsapp.data.error.NetworkErrorHandler
import com.example.eventsapp.data.service.EventService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single { EventService(provideEventService(get()), get<NetworkErrorHandler>()) }
    factory { NetworkErrorHandler() }
    single{ provideMyRetrofit(androidContext()) }
}

private fun provideEventService(retrofit: Retrofit) = retrofit.create(EventEndpoint::class.java)

