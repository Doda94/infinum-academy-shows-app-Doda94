package com.doda.shows.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class ShowsEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id : String,
    @ColumnInfo(name = "average_rating") val average_rating: Float?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "img_url") val img_url: String?,
    @ColumnInfo(name = "no_of_reviews") val no_of_reviews : Int,
    @ColumnInfo(name = "title") val title: String
)
