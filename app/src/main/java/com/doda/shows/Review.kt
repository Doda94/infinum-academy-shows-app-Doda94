package com.doda.shows

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    @SerialName("id") val id : String,
    @SerialName("comment") val comment  : String?,
    @SerialName("rating") val rating: Int,
    @SerialName("show_id") val show_id: Int,
    @SerialName("user") val user: User
)