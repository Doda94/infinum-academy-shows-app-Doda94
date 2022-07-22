package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.FragmentShowDetailsBinding

class ShowDetailsFragment : Fragment() {

    private var reviews: List<Review> = listOf()

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

        initReviewsRecycler()
        addShowInfo()
        adapter.updateReviews(reviews)
        showReviews()

        binding.writeReviewButton.setOnClickListener {
            openReviewBottomSheet()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        initSubmitReviewListener()

    }

    private fun openReviewBottomSheet() {
        val directions = ShowDetailsFragmentDirections.actionShowDetailsFragmentToAddReviewBottomSheetFragment()
        findNavController().navigate(directions)
    }

    private fun initReviewsRecycler() {
        adapter = ReviewsAdapter(reviews)

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(activity)

        binding.reviewsRecyclerView.adapter = adapter

        binding.reviewsRecyclerView.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
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
        // TODO: add blank img
        val imageResourceId: Int = args.showImg
        binding.showMenuImage.setImageResource(imageResourceId)
        binding.showMenuDescription.text = args.showDesc
    }

    private fun initSubmitReviewListener() {
        parentFragmentManager.setFragmentResultListener("reviewKey", this) { _, bundle ->
            val comment = bundle.getString("comment")
            val rating = bundle.getInt("rating")
            adapter.addReview(Review(args.username,rating, comment.toString()))
            updateRatingBar()
            showReviews()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
