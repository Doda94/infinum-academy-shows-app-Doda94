package com.doda.shows

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class LoginResponse(
    @SerialName("user") val user: User,
)
