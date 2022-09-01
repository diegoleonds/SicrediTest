package com.example.eventsapp.data.repository

import com.example.eventsapp.data.datasource.EventDataSource
import com.example.eventsapp.data.error.ErrorHandler
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.EventPerson
import com.example.eventsapp.data.model.Result

interface EventRepository {
    suspend fun getEvents(): Result<List<Event>>
    suspend fun getEventDetails(id: Long): Result<Event>
    suspend fun checkIn(eventPerson: EventPerson): Result<Unit>
}

class EventRepositoryImpl(
    val dataSource: EventDataSource,
    val errorHandler: ErrorHandler
) : EventRepository {
    override suspend fun getEvents(): Result<List<Event>> {
        return wrapperInResult { dataSource.getEvents() }
    }

    override suspend fun getEventDetails(id: Long): Result<Event> {
        return wrapperInResult { dataSource.getEventDetails(id) }
    }

    override suspend fun checkIn(eventPerson: EventPerson): Result<Unit> {
        return wrapperInResult { dataSource.checkIn(eventPerson) }
    }

    private suspend fun <T> wrapperInResult(block: suspend () -> T): Result<T> {
        return try {
            Result.Success(block())
        } catch (t: Throwable) {
            Result.Fail(errorHandler.getError(t))
        }
    }
}