package com.doda.shows.ui.register

import com.doda.shows.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse (
    @SerialName("user") val user: User,
)