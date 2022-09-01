package com.example.eventsapp.ui.extensions

import android.view.View

fun View.gone() {
    if (visibility == View.VISIBLE)
        visibility = View.GONE
}

fun View.visible() {
    if (visibility == (View.GONE))
        visibility = View.VISIBLE
}