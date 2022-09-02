package com.example.eventsapp.ui.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setError(
    errorCondition: Boolean,
    errorMessage: String
) {
    isErrorEnabled = errorCondition
    if (errorCondition)
        error = errorMessage
}