package com.doda.shows.ui.login

import com.doda.shows.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("user") val user: User,
)
