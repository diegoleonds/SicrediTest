package com.example.eventsapp.ui.fragment.eventlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.Result
import com.example.eventsapp.data.service.EventService
import com.example.eventsapp.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

internal class EventListViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: EventService = mockk()
    private val viewModel = EventListViewModel(service)

    @Test
    fun `State should be set as Success when service getEvents() is successful`() {
        val events = emptyList<Event>()
        coEvery { service.getEvents() } returns Result.Success(events)

        viewModel.fetchEvents()
        assertTrue(viewModel.state.value is UiState.Success)
        assertEquals(events, (viewModel.state.value as UiState.Success).events)
    }

    @Test
    fun `State should be set as Error when service getEvents() is not successful`() {
        coEvery { service.getEvents() } returns Result.Fail(mockk())

        viewModel.fetchEvents()
        assertTrue(viewModel.state.value is UiState.Error)
    }
}