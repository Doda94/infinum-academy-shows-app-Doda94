package com.doda.shows

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "pending_reviews")
data class PendingReview(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "show_id") val show_id: Int,
    @ColumnInfo(name = "user") val user: User,
)
