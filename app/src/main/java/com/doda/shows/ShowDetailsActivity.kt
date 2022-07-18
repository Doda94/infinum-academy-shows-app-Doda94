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

        binding= ActivityShowDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        reviews = listOf()

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

        bottomSheetBinding.writeRatingBar.rating = adapter.average

        bottomSheetBinding.submitReviewButton.setOnClickListener{
            val name: String = intent.getStringExtra("username").toString()
            var desc: String = bottomSheetBinding.reviewTextInputEditText.text.toString()
            if (desc.isEmpty()) {
                desc = ""
            }
            val rating: Int = bottomSheetBinding.writeRatingBar.rating.toInt()

            adapter.addReview(Review(name, rating, desc))
            updateRatingBar()

            dialog.dismiss()
        }
    }

    private fun updateRatingBar(){
        binding.reviewsText.text = getString(R.string.rating_bar_text, adapter.itemCount, adapter.average)
        binding.ratingBar.rating = adapter.average
    }

    private fun addShowInfo(){
        binding.toolbarLayout.title = intent.getStringExtra("show_name")
        // TODO: add blank img
        val imageResourceId: Int = intent.getIntExtra("show_img_id",R.drawable.ic_office)
        binding.showMenuImage.setImageResource(imageResourceId)
        binding.showMenuDescription.text = intent.getStringExtra("show_desc")
    }


}