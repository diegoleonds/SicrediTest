package com.example.eventsapp.ui.di

import com.example.eventsapp.ui.fragment.entry.EntryViewModel
import com.example.eventsapp.ui.fragment.eventdetails.EventDetailsViewModel
import com.example.eventsapp.ui.fragment.eventlist.EventListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { EntryViewModel() }
    viewModel { EventDetailsViewModel(get()) }
    viewModel { EventListViewModel(get()) }
}