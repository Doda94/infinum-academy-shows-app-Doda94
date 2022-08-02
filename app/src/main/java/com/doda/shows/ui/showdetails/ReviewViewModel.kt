package com.doda.shows.ui.showdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel : ViewModel() {

    private var reviews = arrayOf<Review>()

    private var _reviewsLiveData = MutableLiveData(reviews)

    val reviewsLiveData: LiveData<Array<Review>> = _reviewsLiveData

    fun loadReviews(id: Int) {
        ApiModule.retrofit.reviews(id).enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        reviews = body.reviews
                        // TODO (Data is not being observed)
                        _reviewsLiveData.value = reviews
                    }
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addReview(rating: Int, comment: String, showId: Int) {
        ApiModule.retrofit.review(ReviewRequest(rating, comment, showId)).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                val body = response.body()
                if (body != null) {
                    reviews += body.review
                    _reviewsLiveData.value = reviews
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}
