package com.example.eventsapp.data.datasource

import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.EventPerson
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventDataSource {
    suspend fun getEvents(): List<Event>
    suspend fun getEventDetails(id: Long): Event
    suspend fun checkIn(eventPerson: EventPerson)
}

interface EventService : EventDataSource {
    @GET("events")
    override suspend fun getEvents(): List<Event>

    @GET("events/{id}")
    override suspend fun getEventDetails(
        @Path("id", encoded = true) id: Long
    ): Event

    @POST("checkin")
    override suspend fun checkIn(@Body eventPerson: EventPerson)
}