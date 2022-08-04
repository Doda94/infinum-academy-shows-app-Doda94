package com.doda.shows.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.doda.shows.Converters
import com.doda.shows.Review

@Database(
    entities = [
        Review::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ReviewsDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: ReviewsDatabase? = null

        fun getDatabase(context: Context): ReviewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(context, ReviewsDatabase::class.java, "reviews_db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = database
                database
            }
        }
    }

    abstract fun reviewsDAO(): ReviewsDAO

}