package com.example.eventsapp.ui.fragment.eventdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.EventPerson
import com.example.eventsapp.data.model.Person
import com.example.eventsapp.data.model.Result
import com.example.eventsapp.data.service.EventService
import com.example.eventsapp.ui.error.UiError
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val service: EventService
) : ViewModel() {
    private val _result = MutableLiveData<Result<Unit>>()
    val result: LiveData<Result<Unit>>
        get() = _result

    fun eventCheckIn(event: Event, person: Person?) {
        _result.value = Result.Loading()
        viewModelScope.launch {
            _result.value = if (person != null) {
                service.checkIn(EventPerson.toEventPerson(event, person))
            } else {
                Result.Fail(UiError.PersonNotFound)
            }
        }
    }
}