package com.example.eventsapp.data.repository

import com.example.eventsapp.data.datasource.EventDataSource
import com.example.eventsapp.data.error.ErrorHandler
import com.example.eventsapp.data.model.EventPerson
import com.example.eventsapp.data.model.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

internal class EventRepositoryImplTest {
    private val errorHandler: ErrorHandler = mockk()
    private val dataSource: EventDataSource = mockk()
    private val repository = EventRepositoryImpl(dataSource, errorHandler)

    private val eventPerson = EventPerson(
        eventId = 1L,
        name = "Test Name",
        email = "test@test.com"
    )

    @Test
    fun `Should return success result when dataSource getEvents is successful`() = runBlocking {
        coEvery { dataSource.getEvents() } returns emptyList()

        assertTrue(repository.getEvents() is Result.Success)
    }

    @Test
    fun `Should return fail result when dataSource getEvents throw an error`() = runBlocking {
        coEvery { dataSource.getEvents() } throws TestException()
        every { errorHandler.getError(any()) } returns mockk()

        assertTrue(repository.getEvents() is Result.Fail)
    }

    @Test
    fun `Should return success result when dataSource getEventDetails is successful`() =
        runBlocking {
            coEvery { dataSource.getEventDetails(any()) } returns mockk()

            assertTrue(repository.getEventDetails(1L) is Result.Success)
        }

    @Test
    fun `Should return fail result when dataSource getEventDetails throw an error`() = runBlocking {
        coEvery { dataSource.getEventDetails(any()) } throws TestException()
        every { errorHandler.getError(any()) } returns mockk()

        assertTrue(repository.getEventDetails(1L) is Result.Fail)
    }

    @Test
    fun `Should return success result when dataSource check in is successful`() = runBlocking {
        coEvery { dataSource.checkIn(any()) } just runs

        assertTrue(repository.checkIn(eventPerson) is Result.Success)
    }

    @Test
    fun `Should return fail result when dataSource check in throw an error`() = runBlocking {
        coEvery { dataSource.checkIn(any()) } throws TestException()
        every { errorHandler.getError(any()) } returns mockk()

        assertTrue(repository.checkIn(eventPerson) is Result.Fail)
    }

    private class TestException : Exception()
}