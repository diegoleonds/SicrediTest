package com.example.eventsapp.data.error

/**
 * Used on repository to handle http errors and to expose them for other layers
 * be able to do this too
 */
sealed class NetworkError : Error {
    object NotFound : NetworkError()
    object AccessDenied : NetworkError()
    object ServiceUnavailable : NetworkError()
    object Unknown : NetworkError()
}