package com.doda.shows.ui.shows

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.json.JSONArray

@Serializable
data class ShowsResponse(
    @SerialName("shows") val shows: String
)
