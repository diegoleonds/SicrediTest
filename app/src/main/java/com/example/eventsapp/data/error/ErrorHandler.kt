package com.example.eventsapp.data.error

interface ErrorHandler {
    fun getError(throwable: Throwable): Error
}