package com.doda.shows.ui.showdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doda.shows.Review
import com.doda.shows.databinding.ItemReviewBinding

class ReviewsAdapter(
    private var items: Array<Review>
) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

     override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun updateReviews(reviews: Array<Review>){
        items = reviews
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(private val binding: ItemReviewBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            binding.reviewTextNumber.text = item.rating.toString()
            binding.reviewName.text = item.user.email
            binding.reviewTextView.text = item.comment
            if (item.user.imageUrl != null){
                Glide.with(binding.reviewProfileImage.context)
                    .load(item.user.imageUrl)
                    .into(binding.reviewProfileImage)
            }
        }
    }
}