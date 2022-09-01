package com.example.eventsapp.data.api

import com.example.eventsapp.data.datasource.EventService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val eventsService: EventService by lazy {
        retrofit.create(EventService::class.java)
    }
}