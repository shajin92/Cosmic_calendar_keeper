package com.sendil.cosmiccalenderkeeper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sendil.cosmiccalenderkeeper.models.MyEvents


@Database(entities = [MyEvents::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {

    abstract fun eventDao(): EventsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}