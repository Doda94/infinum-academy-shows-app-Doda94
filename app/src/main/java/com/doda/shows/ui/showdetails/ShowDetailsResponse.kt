package com.doda.shows.ui.showdetails

import com.doda.shows.Show
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowDetailsResponse(
    @SerialName("show") val show: Show
)
