package com.example.eventsapp.data.model

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals

internal class EventPersonTest {
    private val person = Person(
        "name",
        "email"
    )
    private val event = Event(
        1,
        "title",
        "description",
        "image",
        2.0,
        1212121,
        ArrayList()
    )
    private val eventPerson = EventPerson(
        event.id,
        person.name,
        person.email
    )

    @Test
    fun `toEventPerson should return expected event person`() {
        assertEquals(eventPerson, EventPerson.toEventPerson(event, person))
    }

    @Test
    fun `toEventPerson should return a different event person`() {
        assertNotEquals(
            eventPerson,
            EventPerson.toEventPerson(event.copy(id = 2), person)
        )
    }
}