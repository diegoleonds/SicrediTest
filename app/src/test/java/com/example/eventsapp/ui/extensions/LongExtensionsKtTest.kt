package com.example.eventsapp.ui.extensions

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class LongExtensionsKtTest {
    @Test
    fun shouldReturnExpectedDate() {
        val expectedDate = "08/20/2018"
        val timestamp = 1534784400000L
        assertEquals(expectedDate, timestamp.toDateString())
    }

    @Test
    fun shouldNotReturnExpectedDate() {
        val expectedDate = "06/24/2021"
        val timestamp = 1621825933L
        assertNotEquals(expectedDate, timestamp.toDateString())
    }
}