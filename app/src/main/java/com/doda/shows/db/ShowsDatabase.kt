package com.doda.shows.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doda.shows.Show
import com.doda.shows.ui.shows.ShowsViewModel

@Database(
    entities = [
        Show::class
    ],
    version = 1
)
abstract class ShowsDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: ShowsDatabase? = null

        fun getDatabase(context: Context): ShowsDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(context, ShowsDatabase::class.java, "shows_db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = database
                database
            }
        }
    }

    abstract fun showsDAO(): ShowsDAO

}