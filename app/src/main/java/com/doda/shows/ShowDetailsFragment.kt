package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.AddReviewBottomSheetBinding
import com.doda.shows.databinding.FragmentShowDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShowDetailsFragment : Fragment() {

    private lateinit var reviews: List<Review>

    private var _binding: FragmentShowDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ReviewsAdapter

    private val args by navArgs<ShowDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviews = listOf()

        initReviewsRecycler()
        addShowInfo()
        adapter.updateReviews(reviews)
        showReviews()

        binding.writeReviewButton.setOnClickListener {
            addReviewBottomSheet()
        }

        binding.toolbar.setNavigationOnClickListener {
//            finish()
        }
    }

    private fun initReviewsRecycler() {
        adapter = ReviewsAdapter(reviews)

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(activity)

        binding.reviewsRecyclerView.adapter = adapter

        binding.reviewsRecyclerView.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
    }

    private fun addReviewBottomSheet() {
        val dialog = BottomSheetDialogFragment()

        val bottomSheetBinding = AddReviewBottomSheetBinding.inflate(layoutInflater)


        bottomSheetBinding.closeBottomSheetButton.setOnClickListener {
            dialog.dismiss()
        }

        bottomSheetBinding.writeRatingBar.rating = adapter.average

        bottomSheetBinding.submitReviewButton.setOnClickListener {
            val name: String = args.username
            var desc: String = bottomSheetBinding.reviewTextInputEditText.text.toString()
            if (desc.isEmpty()) {
                desc = ""
            }
            val rating: Int = bottomSheetBinding.writeRatingBar.rating.toInt()
            if (rating == 0) {
//                Toast.makeText(this, getString(R.string.rating_error), Toast.LENGTH_SHORT).show()
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
        binding.toolbarLayout.title = args.showName
//        // TODO: add blank img
        val imageResourceId: Int = args.showImgId
        binding.showMenuImage.setImageResource(imageResourceId)
        binding.showMenuDescription.text = args.showDesc
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}