package com.doda.shows.ui.showdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.Review
import com.doda.shows.Show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsViewModel : ViewModel() {

    private var show: Show? = null

    private var _showDetailsLiveData = MutableLiveData(show)

    val showDetailsLiveData: LiveData<Show?> = _showDetailsLiveData

    fun loadShowDetails(id: String) {
        ApiModule.retrofit.showDetails(id).enqueue(object : Callback<ShowDetailsResponse> {
            override fun onResponse(call: Call<ShowDetailsResponse>, response: Response<ShowDetailsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        show = body.show
                        _showDetailsLiveData.value = show
                    }
                }
            }

            override fun onFailure(call: Call<ShowDetailsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}
