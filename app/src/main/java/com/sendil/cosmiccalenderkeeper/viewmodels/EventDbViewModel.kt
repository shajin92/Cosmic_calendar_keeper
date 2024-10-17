package com.sendil.cosmiccalenderkeeper.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sendil.cosmiccalenderkeeper.models.MyEvents
import com.sendil.cosmiccalenderkeeper.repository.EventsRepository
import kotlinx.coroutines.launch


class EventDbViewModel(private val repository: EventsRepository) : ViewModel() {

    val allUsers: LiveData<List<MyEvents>> = repository.allUsers

    fun insert(user: MyEvents) = viewModelScope.launch {
        repository.insert(user)
    }
}