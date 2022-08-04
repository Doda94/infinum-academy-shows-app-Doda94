package com.doda.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doda.shows.db.ShowsDatabase
import com.doda.shows.ui.shows.ShowsViewModel
import java.lang.IllegalArgumentException

class ShowsViewModelFactory(
    val database: ShowsDatabase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowsViewModel::class.java)) {
            return ShowsViewModel(database) as T
        }
        throw IllegalArgumentException("")
    }
}