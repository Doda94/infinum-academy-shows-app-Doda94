package com.doda.shows.ui.showdetails

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
import com.bumptech.glide.Glide
import com.doda.shows.ApiModule
import com.doda.shows.R
import com.doda.shows.Review
import com.doda.shows.Show
import com.doda.shows.databinding.FragmentShowDetailsBinding

class ShowDetailsFragment : Fragment() {

    private var _binding: FragmentShowDetailsBinding? = null

    private val binding get() = _binding!!

    private var show: Show? = null

    private var reviews = arrayOf<Review>()

    private lateinit var adapter: ReviewsAdapter

    private val viewModel by viewModels<ShowDetailsViewModel>()

    private val reviewViewModel by viewModels<ReviewViewModel>()

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
        ApiModule.initRetrofit(requireContext())
        viewModel.loadShowDetails(args.id)
        reviewViewModel.loadReviews(args.id.toInt())

        viewModel.showDetailsLiveData.observe(viewLifecycleOwner) { showDetailsLiveData ->
            show = showDetailsLiveData
            show?.let { addShowInfo(it) }
        }

        reviewViewModel.reviewsLiveData.observe(viewLifecycleOwner){ reviewsLiveData ->
            reviews = reviewsLiveData
            initReviewsRecycler(reviews)
            adapter.updateReviews(reviewsLiveData)
            adapter.notifyItemInserted(reviews.size)
            showReviews()
        }

        binding.writeReviewButton.setOnClickListener {
            openReviewBottomSheet()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun initReviewsRecycler(reviews: Array<Review>) {
                adapter = ReviewsAdapter(reviews)

                binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(activity)

                binding.reviewsRecyclerView.adapter = adapter

                binding.reviewsRecyclerView.addItemDecoration(
                    DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
                )
    }

    private fun showReviews() {
        binding.noReviewsConstraintLayout.isVisible = false
        binding.reviewsConstraintLayout.isVisible = true
    }

    private fun openReviewBottomSheet() {
        val directions = ShowDetailsFragmentDirections.actionShowDetailsFragmentToAddReviewBottomSheetFragment(args.id.toInt())
        findNavController().navigate(directions)
    }

    private fun addShowInfo(show: Show) {
        binding.toolbarLayout.title = show.title
        Glide.with(binding.showMenuImage.context)
            .load(show.img_url)
            .into(binding.showMenuImage)
        binding.showMenuDescription.text = show.desc
        if (show.no_of_reviews > 0 && show.average_rating != null) {
            binding.reviewsText.text = getString(R.string.rating_bar_text, show.no_of_reviews, show.average_rating)
            binding.ratingBar.rating = show.average_rating
            showReviews()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
