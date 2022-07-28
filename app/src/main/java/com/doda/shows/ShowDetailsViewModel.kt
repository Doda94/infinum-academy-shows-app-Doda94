package com.doda.shows

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowDetailsViewModel : ViewModel() {

    private var reviews: List<Review> = listOf()

    private var reviewLiveData_ = MutableLiveData(reviews)

    val reviewLiveData get() = reviewLiveData_

    fun addReview(review: Review) {
        reviews = reviews + review
        reviewLiveData.value = reviews
    }

    fun getAverage(): Float {
        var sum: Float = 0F
        for (review in reviews) {
            sum += review.rating.toFloat()
        }
        return (sum / reviews.size.toFloat())
    }

    fun getNumberOfReviews(): Int {
        return reviews.size
    }

}
