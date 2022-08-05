package com.doda.shows.ui.showdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.Show
import com.doda.shows.db.ShowsDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsViewModel(private val database: ShowsDatabase) : ViewModel() {

    private var show: Show? = null

    private var _showDetailsLiveData = MutableLiveData(show)

    val showDetailsLiveData: LiveData<Show?> = _showDetailsLiveData

    private var _showRatingLiveData = MutableLiveData(0F)

    val showRatingLiveData: LiveData<Float> = _showRatingLiveData

    private var _showReviewsNumLiveData = MutableLiveData(0)

    val showReviewsNumLiveData: LiveData<Int> = _showReviewsNumLiveData

    private var _canGetShowDetailsLiveData = MutableLiveData(true)

    val canGetShowDetailsLiveData: LiveData<Boolean> = _canGetShowDetailsLiveData


    fun loadShowDetails(id: String) {
        ApiModule.retrofit.showDetails(id).enqueue(object : Callback<ShowDetailsResponse> {
            override fun onResponse(call: Call<ShowDetailsResponse>, response: Response<ShowDetailsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _canGetShowDetailsLiveData.value = true
                    if (body != null) {
                        _showDetailsLiveData.value = body.show
                        _showRatingLiveData.value = body.show.average_rating
                        _showReviewsNumLiveData.value = body.show.no_of_reviews
                    }
                }
            }

            override fun onFailure(call: Call<ShowDetailsResponse>, t: Throwable) {
                _canGetShowDetailsLiveData.value = false
            }

        })
    }

}
