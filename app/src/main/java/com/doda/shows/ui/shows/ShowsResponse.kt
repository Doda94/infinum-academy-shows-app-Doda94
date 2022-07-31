package com.doda.shows.ui.shows

import com.doda.shows.Show
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowsResponse(
    @SerialName("shows") val shows: Array<Show>
)
