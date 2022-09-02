package com.example.eventsapp.ui.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(): String {
    val dateFormatted = SimpleDateFormat("MM/dd/yyyy")
    val date = Date(this)
    return dateFormatted.format(date)
}