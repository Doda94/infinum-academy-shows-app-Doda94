package com.doda.shows

import android.app.Application

class ShowsApplication : Application() {

    private val Shows = listOf(
        Show(0, "TheOffice", "test", R.drawable.ic_office),
        Show(1, "Stranger Things", "test1", R.drawable.ic_stranger_things),
    )

    fun getShows(): List<Show> {
        return Shows
    }

}