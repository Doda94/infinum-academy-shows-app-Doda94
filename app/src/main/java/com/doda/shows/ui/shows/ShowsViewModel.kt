package com.doda.shows.ui.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.Show
import com.doda.shows.db.ShowsDatabase
import java.util.concurrent.Executors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsViewModel(
    private val database: ShowsDatabase
) : ViewModel() {

    var shows = arrayOf<Show>()

    private var _showsDbLiveData: LiveData<Array<Show>> = database.showsDAO().getAllShows()

    val showsDbLiveData: LiveData<Array<Show>> = _showsDbLiveData

    fun updateDBLiveData(){
        _showsDbLiveData = database.showsDAO().getAllShows()
    }

    fun onGetShowsButtonClicked() {
        ApiModule.retrofit.shows().enqueue(object : Callback<ShowsResponse> {
            override fun onResponse(call: Call<ShowsResponse>, response: Response<ShowsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        shows = body.shows
                        Executors.newSingleThreadExecutor().execute {
                            database.showsDAO().insertAllShows(shows)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
            }
        })
    }
}