package com.doda.shows.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doda.shows.R
import com.doda.shows.databinding.ViewProfileToolbarBinding
import com.doda.shows.ui.shows.ShowsFragmentDirections

class ProfileToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): Toolbar(context, attrs, defStyleAttr)  {

    var binding: ViewProfileToolbarBinding

    init{
        binding = ViewProfileToolbarBinding.inflate(LayoutInflater.from(context), this)
    }

    fun openBottomSheet(directions: NavDirections){
        findNavController().navigate(directions)
    }

    fun setTitle(titleString: String){
        binding.toolbarLayout.title = titleString
    }

    fun setProfilePhoto(imgUrl: String?) = with(binding){
        if (imgUrl != null){
            Glide
                .with(context)
                .load(imgUrl)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_profile_placeholder))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(profileIcon)
        }
        else{
            Glide
                .with(context)
                .load(ContextCompat.getDrawable(context, R.drawable.ic_profile_placeholder))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(profileIcon)
        }
    }

}