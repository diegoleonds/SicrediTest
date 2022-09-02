package com.example.eventsapp.data.di

import com.example.eventsapp.data.endpoint.EventEndpoint
import com.example.eventsapp.data.error.NetworkErrorHandler
import com.example.eventsapp.data.service.EventService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { EventService(provideEventService(get()), get<NetworkErrorHandler>()) }
    factory { NetworkErrorHandler() }
    single{ provideMyRetrofit() }
}

private fun provideMyRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://5f5a8f24d44d640016169133.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideEventService(retrofit: Retrofit) = retrofit.create(EventEndpoint::class.java)