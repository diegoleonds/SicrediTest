package com.example.eventsapp.data.error

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import retrofit2.HttpException
import java.net.HttpURLConnection

internal class NetworkErrorHandlerTest {
    val errorHandler = NetworkErrorHandler()
    val mockkHttpException = mockk<HttpException>()

    @Test
    fun shouldReturnNotFoundWhenReceiveSameError() {
        val expectedError = NetworkError.NotFound
        every { mockkHttpException.code() } returns HttpURLConnection.HTTP_NOT_FOUND

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldNotReturnNotFoundWhenReceiveDiferentError() {
        val expectedError = NetworkError.NotFound
        every { mockkHttpException.code() } returns -1

        Assert.assertNotEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldReturnForbiddenWhenReceiveSameError() {
        val expectedError = NetworkError.AccessDenied
        every { mockkHttpException.code() } returns HttpURLConnection.HTTP_FORBIDDEN

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldNotReturnForbiddenWhenReceiveDiferentError() {
        val expectedError = NetworkError.AccessDenied
        every { mockkHttpException.code() } returns -1

        Assert.assertNotEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldReturnUnavailableWhenReceiveSameError() {
        val expectedError = NetworkError.ServiceUnavailable
        every { mockkHttpException.code() } returns HttpURLConnection.HTTP_UNAVAILABLE

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldNotReturnUnavailableWhenReceiveDiferentError() {
        val expectedError = NetworkError.ServiceUnavailable
        every { mockkHttpException.code() } returns -1

        Assert.assertNotEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldReturnUnknownWhenErrorIsNotDefined() {
        val expectedError = NetworkError.Unknown
        every { mockkHttpException.code() } returns -1

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }
}