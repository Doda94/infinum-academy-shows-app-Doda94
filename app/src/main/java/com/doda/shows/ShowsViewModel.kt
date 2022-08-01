package com.doda.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowsViewModel : ViewModel() {

    val shows = listOf(
        Show(0, "TheOffice", "test", R.drawable.ic_office),
        Show(1, "Stranger Things", "test1", R.drawable.ic_stranger_things),
    )

    private val _showsLiveData = MutableLiveData(
        shows
    )

    val showsliveData: LiveData<List<Show>> = _showsLiveData
}
