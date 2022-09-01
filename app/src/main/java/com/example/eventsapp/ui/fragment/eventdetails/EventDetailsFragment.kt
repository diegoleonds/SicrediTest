package com.example.eventsapp.ui.fragment.eventdetails

import androidx.fragment.app.Fragment
import com.example.eventsapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsFragment : Fragment(R.layout.fragment_event_details) {
    private val viewModel: EventDetailsViewModel by viewModel()
}