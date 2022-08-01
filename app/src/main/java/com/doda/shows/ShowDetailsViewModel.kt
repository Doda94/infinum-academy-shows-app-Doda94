package com.doda.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowDetailsViewModel : ViewModel() {

    private var reviews: List<Review> = listOf()

    private var _reviewLiveData = MutableLiveData(reviews)

    val reviewLiveData: LiveData<List<Review>> = _reviewLiveData

    private var _ratingLiveData = MutableLiveData (0F)

    val ratingLiveData: LiveData<Float> = _ratingLiveData

    private var _numberOfReviewsLiveData = MutableLiveData(0)

    val numberOfReviewsLiveData: LiveData<Int> = _numberOfReviewsLiveData

    fun addReview(review: Review) {
        reviews = reviews + review
        _reviewLiveData.value = reviews
        var sum: Float = 0F
        for (review in reviews) {
            sum += review.rating.toFloat()
        }
        _ratingLiveData.value = sum / reviews.size.toFloat()
        _numberOfReviewsLiveData.value = reviews.size
    }

}
