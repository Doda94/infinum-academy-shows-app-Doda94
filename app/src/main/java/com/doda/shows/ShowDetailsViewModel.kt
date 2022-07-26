package com.doda.shows

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowDetailsViewModel : ViewModel() {

    private var reviews = mutableListOf(
        Review("test", 5, "test"),
        Review("test1", 3, "test"),
    )

    private var reviewLiveData_ = MutableLiveData(reviews)

    val reviewLiveData get() = reviewLiveData_

    fun addReview (review: Review){
        reviews.add(review)
    }

}
