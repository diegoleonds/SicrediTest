package com.example.eventsapp.ui.fragment.eventlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.Result
import com.example.eventsapp.data.service.EventService
import kotlinx.coroutines.launch

class EventListViewModel(
    private val service: EventService
) : ViewModel() {
    private val _result = MutableLiveData<Result<List<Event>>>()
    val result: LiveData<Result<List<Event>>>
        get() = _result

    fun fetchEvents() {
        _result.value = Result.Loading()
        viewModelScope.launch {
            _result.value = service.getEvents()
        }
    }
}