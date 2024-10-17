package com.sendil.cosmiccalenderkeeper.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sendil.cosmiccalenderkeeper.models.Events
import com.sendil.cosmiccalenderkeeper.models.EventsItem
import com.sendil.cosmiccalenderkeeper.utils.StorageUtils
import kotlinx.coroutines.flow.MutableStateFlow

class EventInfoViewModel : ViewModel() {
    private val jsonFileName = "2024.json"

    val events = MutableStateFlow(mutableListOf<EventsItem>())


    fun getTotalEvents(context: Context) {
        val storageUtils = StorageUtils(context)

        val allEvents = Gson().fromJson(
            storageUtils.getJsonFromAssets(jsonFileName),
            Events::class.java
        )

        events.value = allEvents


    }



}