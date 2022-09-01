package com.example.eventsapp.ui.fragment.eventdetails

import androidx.lifecycle.ViewModel
import com.example.eventsapp.data.repository.EventRepository

class EventDetailsViewModel(
    val repository: EventRepository
) : ViewModel() {
}