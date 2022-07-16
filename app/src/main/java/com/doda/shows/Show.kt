package com.doda.shows

import androidx.annotation.DrawableRes

data class Show(
    val name: String,
    val description: String,
    @DrawableRes val imageResourceId: Int,
)
