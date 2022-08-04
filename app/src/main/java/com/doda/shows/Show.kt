package com.doda.shows

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "shows")
data class Show(
    @SerialName("id") @ColumnInfo(name = "id") @PrimaryKey val id : String,
    @SerialName("average_rating") @ColumnInfo(name = "average_rating") val average_rating: Float?,
    @SerialName("description") @ColumnInfo(name = "desc") val desc: String?,
    @SerialName("image_url") @ColumnInfo(name = "img_url") val img_url: String?,
    @SerialName("no_of_reviews") @ColumnInfo(name = "no_of_reviews") val no_of_reviews : Int,
    @SerialName("title") @ColumnInfo(name = "title") val title: String
)
