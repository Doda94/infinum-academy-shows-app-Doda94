package com.doda.shows

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.ActivityShowDetailsBinding
import com.doda.shows.databinding.AddReviewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShowDetailsActivity : AppCompatActivity() {

    private var reviews: List<Review> = listOf()

    private lateinit var binding: ActivityShowDetailsBinding

    private lateinit var adapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initReviewsRecycler()
        addShowInfo()
        adapter.updateReviews(reviews)
        showReviews()

        binding.writeReviewButton.setOnClickListener {
            addReviewBottomSheet()
        }

        binding.toolbar.setNavigationOnClickListener{
            finish()
        }

    }

    private fun initReviewsRecycler() {
        adapter = ReviewsAdapter(reviews)

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.reviewsRecyclerView.adapter = adapter

        binding.reviewsRecyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    private fun addReviewBottomSheet() {
        val dialog = BottomSheetDialog(this, R.style.Theme_Shows)
        val bottomSheetBinding = AddReviewBottomSheetBinding.inflate(layoutInflater)

        dialog.setContentView(bottomSheetBinding.root)

        dialog.show()

        bottomSheetBinding.closeBottomSheetButton.setOnClickListener {
            dialog.dismiss()
        }

        bottomSheetBinding.writeRatingBar.rating = adapter.average

        bottomSheetBinding.submitReviewButton.setOnClickListener {
            val name: String = intent.getStringExtra("username").toString()
            var desc: String = bottomSheetBinding.reviewTextInputEditText.text.toString()
            val rating: Int = bottomSheetBinding.writeRatingBar.rating.toInt()
            if (rating == 0) {
                Toast.makeText(this, getString(R.string.rating_error), Toast.LENGTH_SHORT).show()
            } else {
                adapter.addReview(Review(name, rating, desc))
                updateRatingBar()
                showReviews()

                dialog.dismiss()
            }
        }
    }

    private fun showReviews() {
        if (adapter.itemCount > 0) {
            binding.noReviewsConstraintLayout.isVisible = false
            binding.reviewsConstraintLayout.isVisible = true
        }
    }

    private fun updateRatingBar() {
        binding.reviewsText.text = getString(R.string.rating_bar_text, adapter.itemCount, adapter.average)
        binding.ratingBar.rating = adapter.average
    }

    private fun addShowInfo() {
        binding.toolbarLayout.title = intent.getStringExtra("show_name")
        // TODO: add blank img
        val imageResourceId: Int = intent.getIntExtra("show_img_id", R.drawable.ic_office)
        binding.showMenuImage.setImageResource(imageResourceId)
        binding.showMenuDescription.text = intent.getStringExtra("show_desc")
    }


}