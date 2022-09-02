package com.example.eventsapp.ui.fragment.entry

import androidx.lifecycle.ViewModel

class EntryViewModel : ViewModel() {
    fun isEmailInvalid(email: String?): Boolean {
        return if (email != null) {
            val emailToValidate = email.replace("\\s".toRegex(), "")
            return emailToValidate.isEmpty() || !emailToValidate.matches(
                Regex(
                    "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]" +
                            "+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\" +
                            "x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@" +
                            "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\" +
                            "[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]" +
                            "?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\" +
                            "x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
                )
            )
        } else {
            true
        }
    }

    fun isNameInvalid(name: String?): Boolean {
        return name?.replace("\\s".toRegex(), "")?.isEmpty() ?: true
    }
}