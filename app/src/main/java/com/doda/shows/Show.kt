package com.doda.shows

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Show(
    @SerialName("id") val id : String,
    @SerialName("average_rating") val average_rating: Float?,
    @SerialName("description") val desc: String?,
    @SerialName("image_url") val img_url: String?,
    @SerialName("no_of_reviews") val no_of_reviews : Int,
    @SerialName("title") val title: String
)