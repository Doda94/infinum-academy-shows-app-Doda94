package com.doda.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doda.shows.databinding.ItemReviewBinding

class ReviewsAdapter(
    private var items: List<Review>
) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    private var ratingSum: Int = 0
    var average : Float = 0F

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context))
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun updateReviews(reviews: List<Review>){
        items = reviews
        notifyDataSetChanged()
    }

    fun addReview(review: Review){
        items += review
        ratingSum += review.rating
        average = (ratingSum.toFloat()/items.size.toFloat())
        notifyItemInserted(items.size)
    }

    inner class ReviewViewHolder(private val binding: ItemReviewBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            binding.reviewTextNumber.text = item.rating.toString()
            binding.reviewName.text = item.name
            binding.reviewTextView.text = item.review
        }
    }
}