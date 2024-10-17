package com.sendil.cosmiccalenderkeeper.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sendil.cosmiccalenderkeeper.models.MyEvents

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: MyEvents)


    @Query("SELECT * FROM  my_events  ORDER BY date ASC")
    fun getAllUsers(): LiveData<List<MyEvents>>
}
