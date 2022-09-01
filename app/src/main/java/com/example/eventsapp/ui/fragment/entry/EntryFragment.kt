package com.example.eventsapp.ui.fragment.entry

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eventsapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntryFragment : Fragment(R.layout.fragment_entry) {
    private val viewModel: EntryViewModel by viewModel()

    private lateinit var emailEditTxt: TextInputEditText
    private lateinit var nameEditTxt: TextInputEditText

    private lateinit var emailLayoutTxt: TextInputLayout
    private lateinit var nameLayoutTxt: TextInputLayout

    private lateinit var enterBtn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        getDataFromSharedPreferences()
        enterBtnClick(view)
    }


    private fun inflateViews(view: View) {
        emailEditTxt = view.findViewById(R.id.email_edit_txt)
        nameEditTxt = view.findViewById(R.id.name_edit_txt)

        emailLayoutTxt = view.findViewById(R.id.email_txt_inputlayout)
        nameLayoutTxt = view.findViewById(R.id.name_txt_inputlayout)

        enterBtn = view.findViewById(R.id.enter_btn)
    }

    private fun enterBtnClick(view: View) {
        enterBtn.setOnClickListener {
            val name = nameEditTxt.text.toString()
            val email = emailEditTxt.text.toString()

            val nameError = viewModel.isNameInvalid(name)
            val emailError = viewModel.isEmailInvalid(email)

            setTxtLayoutError(nameError, nameLayoutTxt, getString(R.string.invalid_name_error))
            setTxtLayoutError(emailError, emailLayoutTxt, getString(R.string.invalid_email_error))

            if (!nameError && !emailError) {
                storeDataIntoSharedPreferences(name, email)
                Navigation.findNavController(view)
                    .navigate(R.id.action_entryFragment_to_eventListFragment)
            }
        }
    }

    private fun setTxtLayoutError(
        errorCondition: Boolean,
        txtLayout: TextInputLayout,
        errorMessage: String
    ) {
        txtLayout.isErrorEnabled = errorCondition
        if (errorCondition)
            txtLayout.error = errorMessage
    }

    private fun storeDataIntoSharedPreferences(name: String, email: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(USERNAME, name)
            putString(EMAIL, email)
            commit()
        }
    }

    private fun getDataFromSharedPreferences() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        sharedPref?.let {
            nameEditTxt.setText(it.getString(USERNAME, ""))
            emailEditTxt.setText(it.getString(EMAIL, ""))
        }
    }

    companion object {
        const val USERNAME = "user_name"
        const val EMAIL = "user_email"
    }
}