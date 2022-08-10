package com.doda.shows

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "reviews")
data class Review(
    @ColumnInfo(name = "id") @SerialName("id") @PrimaryKey val id : String,
    @ColumnInfo(name = "comment") @SerialName("comment") val comment  : String,
    @ColumnInfo(name = "rating") @SerialName("rating") val rating: Int,
    @ColumnInfo(name = "show_id") @SerialName("show_id") val show_id: Int,
    @ColumnInfo(name = "user") @SerialName("user") val user: User,
)