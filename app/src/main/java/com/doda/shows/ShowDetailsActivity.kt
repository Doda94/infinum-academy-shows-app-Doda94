package com.doda.shows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.ActivityShowDetailsBinding

class ShowDetailsActivity : AppCompatActivity() {

    private lateinit var reviews: List<Review>

    private lateinit var binding : ActivityShowDetailsBinding

    private lateinit var adapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviews = listOf(
            Review("Marko Dodig", 5),
            Review("Pero Peric", 3),
        )

        binding= ActivityShowDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }

    private fun initReviewsRecycler(){
        adapter = ReviewsAdapter(reviews)

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.reviewsRecyclerView.adapter = adapter
    }
}