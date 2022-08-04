package com.doda.shows.ui.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.doda.shows.R
import com.doda.shows.Show
import com.doda.shows.databinding.ViewShowItemBinding

class ShowsAdapter(
    private var items: Array<Show>,
    private val onItemClickCallback: (Show) -> Unit
) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ViewShowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
    fun loadShows(shows: Array<Show>) {
        items = shows
        notifyDataSetChanged()
    }

    inner class ShowViewHolder(private val binding: ViewShowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Show) {
            binding.showName.text = item.title
            binding.showDesc.text = item.desc
            val drawable = CircularProgressDrawable(binding.showImage.context)
            drawable.setColorSchemeColors(R.color.shows_purple)
            drawable.centerRadius = 50f
            drawable.strokeWidth = 5f
            drawable.start()

            Glide.with(binding.showImage.context)
                .load(item.img_url)
                .placeholder(drawable)
                .into(binding.showImage)
            binding.cardContainer.setOnClickListener { onItemClickCallback(item) }
        }

    }


}