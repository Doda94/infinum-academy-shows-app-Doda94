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
            Review("Mia Dodig", 2),
            Review("Dusko Latinovic", 5),
        )

        binding= ActivityShowDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)


        initReviewsRecycler()
        addShowInfo()
        adapter.updateReviews(reviews)

    }

    private fun initReviewsRecycler(){
        adapter = ReviewsAdapter(reviews)

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.reviewsRecyclerView.adapter = adapter
    }

    private fun addShowInfo(){
        binding.toolbarLayout.title = intent.getStringExtra("show_name")
        // TODO: add blank img
        val imageResourceId: Int = intent.getIntExtra("show_img_id",R.drawable.ic_office)
        binding.showMenuImage.setImageResource(imageResourceId)
        binding.showMenuDescription.text = intent.getStringExtra("show_desc")
    }
}