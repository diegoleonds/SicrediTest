package com.example.eventsapp.data.di

import com.example.eventsapp.data.datasource.EventService
import com.example.eventsapp.data.error.NetworkErrorHandler
import com.example.eventsapp.data.repository.EventRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { EventRepositoryImpl(provideEventService(get()), get<NetworkErrorHandler>()) }
    factory { NetworkErrorHandler() }
    single{ provideRetrofit() }
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideEventService(retrofit: Retrofit) = retrofit.create(EventService::class.java)