package com.doda.shows

import android.app.Application
import com.doda.shows.db.ShowsDatabase
import java.util.concurrent.Executors

class ShowsApplication : Application() {

    val database by lazy {
        ShowsDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        Executors.newSingleThreadExecutor().execute{
//            database.showsDAO().insertAllShows(shows)
        }
    }

}