package com.sendil.cosmiccalenderkeeper.models

import com.google.gson.annotations.SerializedName

class Events : ArrayList<EventsItem>()


data class EventsItem(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("month")
    val month: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: String = ""
)