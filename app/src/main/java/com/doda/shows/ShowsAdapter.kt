package com.doda.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doda.shows.databinding.ViewShowItemBinding

class ShowsAdapter(
    private var items: List<Show>,
    private val onItemClickCallback: (Show) -> Unit
) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ViewShowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
    fun loadShows(shows: List<Show>) {
        items = shows
        notifyDataSetChanged()
    }

    fun getShowName(show: Show): String {
        return show.name
    }

    fun getShowDesc(show: Show): String {
        return show.description
    }

    fun getShowImg(show: Show): Int {
        return show.imageResourceId
    }

    inner class ShowViewHolder(private val binding: ViewShowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Show) {
            binding.showName.text = item.name
            binding.showDesc.text = item.description
            binding.showImage.setImageResource(item.imageResourceId)
            binding.cardContainer.setOnClickListener { onItemClickCallback(item) }
        }

    }


}