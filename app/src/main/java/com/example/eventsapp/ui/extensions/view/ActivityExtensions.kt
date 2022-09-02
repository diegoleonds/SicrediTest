package com.example.eventsapp.ui.extensions.view

import android.app.Activity
import android.content.Context
import com.example.eventsapp.data.model.Person

private object ActivityConstants {
    const val USERNAME = "username"
    const val USER_EMAIL = "user_email"
}

fun Activity.getPersonFromSharedPreferences(): Person? {
    return getPreferences(Context.MODE_PRIVATE)?.let {
        Person(
            it.getString(ActivityConstants.USERNAME, "") ?: "",
            it.getString(ActivityConstants.USER_EMAIL, "") ?: ""
        )
    }
}

fun Activity.storePersonIntoSharedPreferences(person: Person) {
    getPreferences(Context.MODE_PRIVATE)?.edit()?.apply {
        putString(ActivityConstants.USERNAME, person.name)
        putString(ActivityConstants.USER_EMAIL, person.email)
        apply()
    }
}