package com.doda.shows.ui.shows

import com.doda.shows.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePhotoUploadResponse(
    @SerialName("user") val user: User
)
