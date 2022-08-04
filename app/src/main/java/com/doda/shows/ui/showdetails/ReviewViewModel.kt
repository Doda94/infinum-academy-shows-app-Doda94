package com.doda.shows.ui.showdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.Review
import com.doda.shows.db.ReviewsDAO
import com.doda.shows.db.ReviewsDatabase
import java.util.concurrent.Executors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel(
    private val database: ReviewsDatabase
) : ViewModel() {

    private var reviews = arrayOf<Review>()

    private lateinit var _reviewsDbLiveData: LiveData<Array<Review>>

    lateinit var reviewsDbLiveData: LiveData<Array<Review>>

    fun updateDbLiveData(showId: String) {
        _reviewsDbLiveData = database.reviewsDAO().getShowReviews(showId)
        reviewsDbLiveData = _reviewsDbLiveData
    }

    fun loadReviews(id: Int) {
        ApiModule.retrofit.reviews(id).enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        reviews = body.reviews
                        for (review in reviews) {
                            Executors.newSingleThreadExecutor().execute {
                                database.reviewsDAO().insertShowReviews(review)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
            }

        })
    }

    fun addReview(rating: Int, comment: String, showId: Int) {
        ApiModule.retrofit.review(ReviewRequest(rating, comment, showId)).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                val body = response.body()
                if (body != null) {
                    reviews += body.review
                    Executors.newSingleThreadExecutor().execute {
                        database.reviewsDAO().insertShowReviews(body.review)
                    }
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                // TODO: error
            }

        })
    }


}
