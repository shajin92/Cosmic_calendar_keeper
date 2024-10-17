package com.sendil.cosmiccalenderkeeper.repository

import androidx.lifecycle.LiveData
import com.sendil.cosmiccalenderkeeper.db.EventsDao
import com.sendil.cosmiccalenderkeeper.models.MyEvents

class EventsRepository(private val userDao: EventsDao) {

    val allUsers: LiveData<List<MyEvents>> = userDao.getAllUsers()

    suspend fun insert(user: MyEvents) {
        userDao.insert(user)
    }

}
