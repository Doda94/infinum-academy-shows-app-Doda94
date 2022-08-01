package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.FragmentShowDetailsBinding

private const val REVIEW_KEY = "reviewKey"
private const val COMMENT = "comment"
private const val RATING = "rating"

class ShowDetailsFragment : Fragment() {

    private var _binding: FragmentShowDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ReviewsAdapter

    private val viewModel by viewModels<ShowDetailsViewModel>()

    private val args by navArgs<ShowDetailsFragmentArgs>()

    private var rating: Float? = null

    private var numberOfReviews: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addShowInfo()
        initReviewsRecycler(listOf())

        binding.writeReviewButton.setOnClickListener {
            openReviewBottomSheet()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.ratingLiveData.observe(viewLifecycleOwner) { ratingLiveData ->
            rating = ratingLiveData
            binding.reviewsText.text = getString(R.string.rating_bar_text, numberOfReviews, rating)
            binding.ratingBar.rating = ratingLiveData
        }

        viewModel.numberOfReviewsLiveData.observe(viewLifecycleOwner) { numberOfReviewsLiveData ->
            numberOfReviews = numberOfReviewsLiveData
            binding.reviewsText.text = getString(R.string.rating_bar_text, numberOfReviews, rating)
        }

        viewModel.reviewLiveData.observe(viewLifecycleOwner) { reviewLiveData ->
            adapter.updateReviews(reviewLiveData)
            showReviews()
        }

        initSubmitReviewListener()

    }

    private fun initReviewsRecycler(reviews: List<Review>) {
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

    private fun openReviewBottomSheet() {
        val directions = ShowDetailsFragmentDirections.actionShowDetailsFragmentToAddReviewBottomSheetFragment()
        findNavController().navigate(directions)
    }

    private fun initSubmitReviewListener() {
        parentFragmentManager.setFragmentResultListener(REVIEW_KEY, this) { _, bundle ->
            val comment = bundle.getString(COMMENT)
            val rating = bundle.getInt(RATING)
            viewModel.addReview(Review(args.username, rating, comment.toString()))
        }
    }

    private fun addShowInfo() {
        binding.toolbarLayout.title = args.showName
        // TODO: add blank img
        val imageResourceId: Int = args.showImg
        binding.showMenuImage.setImageResource(imageResourceId)
        binding.showMenuDescription.text = args.showDesc
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
