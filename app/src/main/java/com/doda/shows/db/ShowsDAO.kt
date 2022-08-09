package com.doda.shows.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doda.shows.Review
import com.doda.shows.Show

@Dao
interface ShowsDAO {

    @Query("SELECT * FROM shows")
    fun getAllShows(): LiveData<Array<Show>>

    @Query("SELECT * FROM shows WHERE id IS :id")
    fun getShow(id: String): LiveData<Show>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllShows(shows: Array<Show>)

    @Query("SELECT * FROM reviews WHERE show_id IS :show_id")
    fun getShowReviews(show_id: String): LiveData<Array<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShowReviews(review: Review)

    @Query("DELETE FROM reviews")
    fun deleteShowReviews()

}