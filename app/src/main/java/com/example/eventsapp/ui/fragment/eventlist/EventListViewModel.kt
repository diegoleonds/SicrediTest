package com.example.eventsapp.ui.fragment.eventlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.Result
import com.example.eventsapp.data.service.EventService
import com.example.eventsapp.ui.extensions.getMessageResource
import kotlinx.coroutines.launch

sealed class UiState {
    data class Success(val events: List<Event>) : UiState()
    data class Error(val messageResource: Int) : UiState()
    object Loading : UiState()
}

class EventListViewModel(
    private val service: EventService
) : ViewModel() {
    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState>
        get() = _state

    fun fetchEvents() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            when (val result = service.getEvents()) {
                is Result.Success -> _state.value = UiState.Success(result.data)
                is Result.Fail -> _state.value = UiState.Error(result.error.getMessageResource())
            }
        }
    }
}