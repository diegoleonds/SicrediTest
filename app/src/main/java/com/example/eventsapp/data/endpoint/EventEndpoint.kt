package com.example.eventsapp.data.endpoint

import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.EventPerson
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventEndpoint {
    @GET("events")
    suspend fun getEvents(): List<Event>

    @GET("events/{id}")
    suspend fun getEventDetails(
        @Path("id", encoded = true) id: Long
    ): Event

    @POST("checkin")
    suspend fun checkIn(@Body eventPerson: EventPerson)
}