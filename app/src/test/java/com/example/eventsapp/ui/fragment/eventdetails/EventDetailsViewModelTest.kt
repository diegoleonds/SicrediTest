package com.example.eventsapp.ui.fragment.eventdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.eventsapp.data.error.NetworkError
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.Person
import com.example.eventsapp.data.model.Result
import com.example.eventsapp.data.service.EventService
import com.example.eventsapp.ui.error.UiError
import com.example.eventsapp.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

internal class EventDetailsViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: EventService = mockk()
    private val viewModel = EventDetailsViewModel(service)

    private val event = Event(
        id = 1L,
        title = "title",
        description = "description",
        image = "image",
        price = 2.0,
        date = 121212121,
        people = emptyList()
    )

    private val person = Person(
        name = "Joaquin",
        email = "emaildojoaquin@email.com"
    )

    @Test
    fun `Should set result as success when service checkIn is successful`() {
        coEvery { service.checkIn(any()) } returns Result.Success(Unit)

        viewModel.eventCheckIn(event, person)
        assertTrue(viewModel.result.value is Result.Success)
    }

    @Test
    fun `Should set result as fail with a NetworkError when service checkIn fail`() {
        coEvery { service.checkIn(any()) } returns Result.Fail(NetworkError.NotFound)

        viewModel.eventCheckIn(event, person)
        assertTrue(viewModel.result.value is Result.Fail)
        assertTrue((viewModel.result.value as Result.Fail).error is NetworkError)
    }

    @Test
    fun `Should set result as fail with a UiError personNotFound when person is null`() {
        viewModel.eventCheckIn(event, null)

        assertTrue(viewModel.result.value is Result.Fail)
        assertTrue((viewModel.result.value as Result.Fail).error is UiError.PersonNotFound)
    }
}