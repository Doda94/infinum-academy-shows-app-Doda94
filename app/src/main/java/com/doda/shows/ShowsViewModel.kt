package com.doda.shows

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowsViewModel : ViewModel() {

    private val showsLiveData_ = MutableLiveData<List<Show>>(
        listOf(
            Show(0, "TheOffice", "test", R.drawable.ic_office),
            Show(1, "Stranger Things", "test1", R.drawable.ic_stranger_things),
        )
    )

    val showsliveData get() = showsLiveData_
}
