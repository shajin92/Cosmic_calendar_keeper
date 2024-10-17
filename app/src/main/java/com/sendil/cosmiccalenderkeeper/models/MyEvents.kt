package com.sendil.cosmiccalenderkeeper.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_events")
data class MyEvents(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val month: String,
    val title: String,
    val type: String
)