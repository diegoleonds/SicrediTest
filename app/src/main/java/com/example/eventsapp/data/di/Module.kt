package com.example.eventsapp.data.di

import com.example.eventsapp.data.api.RetrofitInstance
import com.example.eventsapp.data.error.NetworkErrorHandler
import com.example.eventsapp.data.repository.EventRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single { EventRepositoryImpl(RetrofitInstance.eventsService, get<NetworkErrorHandler>()) }
    factory { NetworkErrorHandler() }
}