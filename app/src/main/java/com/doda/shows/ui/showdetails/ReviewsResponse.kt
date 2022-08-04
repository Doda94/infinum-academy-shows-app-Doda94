package com.doda.shows.ui.showdetails

import com.doda.shows.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsResponse(
    @SerialName("reviews") val reviews: Array<Review>
)
