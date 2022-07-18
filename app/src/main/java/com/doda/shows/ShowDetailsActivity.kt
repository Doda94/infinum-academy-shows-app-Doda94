package com.doda.shows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.ActivityShowDetailsBinding
import com.doda.shows.databinding.AddReviewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShowDetailsActivity : AppCompatActivity() {

    private lateinit var reviews: List<Review>

    private lateinit var binding : ActivityShowDetailsBinding

    private lateinit var adapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviews = listOf(
            Review("Marko Dodig", 5F,""),
            Review("Pero Peric", 3F,""),
            Review("Mia Dodig", 2F,"zasu serija"),
            Review("Dusko Latinovic", 5F,getString(R.string.sample_text)),
        )

        binding= ActivityShowDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)


        initReviewsRecycler()
        addShowInfo()
        adapter.updateReviews(reviews)

        binding.writeReviewButton.setOnClickListener{
            addReviewBottomSheet()
        }

    }

    private fun initReviewsRecycler(){
        adapter = ReviewsAdapter(reviews)

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.reviewsRecyclerView.adapter = adapter

        binding.reviewsRecyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    private fun addReviewBottomSheet(){
        val dialog = BottomSheetDialog(this)

        val bottomSheetBinding = AddReviewBottomSheetBinding.inflate(layoutInflater)

        dialog.setContentView(bottomSheetBinding.root)

        dialog.show()

        bottomSheetBinding.closeBottomSheetButton.setOnClickListener{
            dialog.dismiss()
        }

        bottomSheetBinding.submitReviewButton.setOnClickListener{
            val name: String = intent.getStringExtra("username").toString()
            val desc: String = bottomSheetBinding.reviewTextInputEditText.text.toString()
            val rating: Float = bottomSheetBinding.writeRatingBar.rating

            adapter.addReview(Review(name, rating, desc))
            dialog.dismiss()
        }
    }

    private fun addShowInfo(){
        binding.toolbarLayout.title = intent.getStringExtra("show_name")
        // TODO: add blank img
        val imageResourceId: Int = intent.getIntExtra("show_img_id",R.drawable.ic_office)
        binding.showMenuImage.setImageResource(imageResourceId)
        binding.showMenuDescription.text = intent.getStringExtra("show_desc")
    }


}