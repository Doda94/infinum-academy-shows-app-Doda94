package com.doda.shows.ui.showdetails

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.PendingReview
import com.doda.shows.Review
import com.doda.shows.User
import com.doda.shows.db.ShowsDatabase
import java.util.concurrent.Executors
import okhttp3.internal.userAgent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val USER_EMAIL = "USER_EMAIL"
private const val IMAGE_URL = "IMAGE_URL"
private const val USER_ID = "USER_ID"

class ReviewViewModel(
    private val database: ShowsDatabase
) : ViewModel() {

    private var reviews = arrayOf<Review>()

    private lateinit var _reviewsDbLiveData: LiveData<Array<Review>>

    lateinit var reviewsDbLiveData: LiveData<Array<Review>>

    fun updateDbLiveData(showId: String) {
        _reviewsDbLiveData = database.showsDAO().getShowReviews(showId)
        reviewsDbLiveData = _reviewsDbLiveData
    }

    private var _canGetData = MutableLiveData(true)

    val canGetData: LiveData<Boolean> = _canGetData

    fun loadReviews(id: Int) {
        ApiModule.retrofit.reviews(id).enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                Executors.newSingleThreadExecutor().execute {
                    database.showsDAO().deleteShowReviews(id.toString())
                }
                if (response.isSuccessful) {
                    val body = response.body()
                    _canGetData.value = true
                    if (body != null) {
                        reviews = body.reviews
                        for (review in reviews) {
                            Executors.newSingleThreadExecutor().execute {
                                database.showsDAO().insertShowReviews(review)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                _canGetData.value = false
            }

        })
    }

    fun uploadOfflineReviews(reviews: Array<Review>) {
        for (it in reviews) {
            uploadReview(it.rating, it.comment, it.show_id, it.id)
        }
    }

    private fun uploadReview(rating: Int, comment: String, showId: Int, id: String) {
        ApiModule.retrofit.review(ReviewRequest(rating, comment, showId)).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    Executors.newSingleThreadExecutor().execute {
                        database.showsDAO().deletePendingReview(id)
                    }
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
            }
        })
    }

    fun addReview(rating: Int, comment: String, showId: Int, sharedPreferences: SharedPreferences, lastId: String) {
        ApiModule.retrofit.review(ReviewRequest(rating, comment, showId)).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                Executors.newSingleThreadExecutor().execute {
                    val pendingReviews = database.showsDAO().getPendingReviews()
                    for (it in pendingReviews) {
                        addReview(it.rating, it.comment!!, it.show_id, sharedPreferences, lastId)
                    }
                    database.showsDAO().deleteAllPendingReviews()
                    database.showsDAO().deleteShowReviews(showId.toString())
                    loadReviews(showId)
                }
                val body = response.body()
                if (body != null) {
                    reviews += body.review
                    Executors.newSingleThreadExecutor().execute {
                        database.showsDAO().insertShowReviews(body.review)
                    }
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                // TODO: try to avoid using !!
                val userId = sharedPreferences.getString(USER_ID, "-1")!!
                val email = sharedPreferences.getString(USER_EMAIL, "john@doe.com")!!
                val imageUrl = sharedPreferences.getString(IMAGE_URL, null)
                val newId = ((lastId.toInt()) + 1).toString()
                Executors.newSingleThreadExecutor().execute {
                    database.showsDAO()
                        .insertPendingShowReviews(PendingReview(newId, comment, rating, showId, User(userId, email, imageUrl)))
                    database.showsDAO()
                        .insertShowReviews(Review(newId, comment, rating, showId, User(userId, email, imageUrl)))
                }
            }
        })
    }


}
