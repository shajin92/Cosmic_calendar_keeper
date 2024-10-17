package com.sendil.cosmiccalenderkeeper.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sendil.cosmiccalenderkeeper.repository.EventsRepository


class EventsDbViewModelFactory(private val repository: EventsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventDbViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventDbViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}