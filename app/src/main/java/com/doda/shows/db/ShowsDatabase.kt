package com.doda.shows.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doda.shows.Converters
import com.doda.shows.PendingReview
import com.doda.shows.Review
import com.doda.shows.Show

@Database(
    entities = [
        Show::class,
        Review::class,
        PendingReview::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
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