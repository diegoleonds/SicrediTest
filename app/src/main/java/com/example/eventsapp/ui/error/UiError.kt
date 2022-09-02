package com.example.eventsapp.ui.error

import com.example.eventsapp.data.error.Error

sealed class UiError : Error {
    object PersonNotFound : UiError()
}
