package com.doda.shows.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doda.shows.PendingReview
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

    @Query("DELETE FROM reviews where show_id is :show_id")
    fun deleteShowReviews(show_id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPendingShowReviews(review: PendingReview)

    @Query("DELETE FROM pending_reviews")
    fun deleteAllPendingReviews()

    @Query("SELECT * FROM pending_reviews")
    fun getPendingReviews(): Array<Review>

    @Query("DELETE FROM pending_reviews WHERE id is :id")
    fun deletePendingReview(id: String)

    @Query("SELECT * FROM pending_reviews where show_id is :show_id")
    fun getShowPendingReviews(show_id: Int): LiveData<Array<Review>>

}