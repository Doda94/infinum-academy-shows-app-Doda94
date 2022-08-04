package com.doda.shows.ui.showdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doda.shows.db.ShowsDatabase
import java.lang.IllegalArgumentException

class ShowDetailsViewModelFactory(
    val database: ShowsDatabase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowDetailsViewModel::class.java)){
            return ShowDetailsViewModel(database) as T
        }
        throw IllegalArgumentException("")
    }
}