package com.example.eventsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventPerson(
    val eventId: Long,
    val name: String,
    val email: String
) : Parcelable