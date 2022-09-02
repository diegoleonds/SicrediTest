package com.example.eventsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: Long,
    val title: String,
    val description: String,
    val image: String,
    val price: Double,
    val date: Long,
    val people: List<Person>
): Parcelable