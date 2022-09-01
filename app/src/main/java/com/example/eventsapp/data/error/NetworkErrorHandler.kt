package com.example.eventsapp.data.error

import retrofit2.HttpException
import java.net.HttpURLConnection

/**
 * Class to convert a throwable into a NetworkError
 */
class NetworkErrorHandler : ErrorHandler {
    override fun getError(throwable: Throwable): NetworkError {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> NetworkError.NotFound
                    HttpURLConnection.HTTP_FORBIDDEN -> NetworkError.AccessDenied
                    HttpURLConnection.HTTP_UNAVAILABLE -> NetworkError.ServiceUnavailable
                    else -> NetworkError.Unknown
                }
            }
            else -> NetworkError.Unknown
        }
    }
}