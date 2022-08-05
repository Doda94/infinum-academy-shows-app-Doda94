package com.doda.shows.ui.showdetails

import android.content.Context
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
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.doda.shows.ApiModule
import com.doda.shows.R
import com.doda.shows.Review
import com.doda.shows.databinding.FragmentShowDetailsBinding
import com.doda.shows.Show
import com.doda.shows.ShowsApplication

private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class ShowDetailsFragment : Fragment() {

    private var _binding: FragmentShowDetailsBinding? = null

    private val binding get() = _binding!!

    private var show: Show? = null

    private var showDb: Show? = null

    private var reviews = arrayOf<Review>()

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
        val reviewViewModel: ReviewViewModel by viewModels {
            ReviewViewModelFactory((activity?.application as ShowsApplication).reviewsDatabase)
        }
        val viewModel: ShowDetailsViewModel by viewModels {
            ShowDetailsViewModelFactory((activity?.application as ShowsApplication).database)
        }
        initReviewsRecycler(reviews)
        ApiModule.initRetrofit(requireContext().getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE))
        viewModel.loadShowDetails(args.id)
        reviewViewModel.updateDbLiveData(args.id)
        reviewViewModel.loadReviews(args.id.toInt())

        viewModel.showDetailsLiveData.observe(viewLifecycleOwner) { showDetailsLiveData ->
            show = showDetailsLiveData
            show?.let { addShowInfo(it) }
        }

        (activity?.application as ShowsApplication).database.showsDAO().getShow(args.id).observe(viewLifecycleOwner) { showDbLiveData ->
            if (show == null) {
                show = showDbLiveData
                show?.let { addShowInfo(it) }
            }
        }

        viewModel.canGetShowDetailsLiveData.observe(viewLifecycleOwner) { canGetShowDetailsLiveData ->
            binding.writeReviewButton.isEnabled = canGetShowDetailsLiveData
        }

        initReviewsLiveDataObserver(reviewViewModel, viewModel)
        initReviewTextLiveDataObserver(viewModel)

        binding.writeReviewButton.setOnClickListener {
            openReviewBottomSheet()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        reviewViewModel.canGetData.observe(viewLifecycleOwner) { canGetData ->
            binding.writeReviewButton.isEnabled = canGetData
        }

    }

    private fun initReviewTextLiveDataObserver(viewModel: ShowDetailsViewModel) {
        var rating = 0F
        var numOfReviews = 0
        viewModel.showRatingLiveData.observe(viewLifecycleOwner) { ratingLiveData ->
            rating = ratingLiveData
            binding.reviewsText.text = getString(R.string.rating_bar_text, numOfReviews, rating)
        }
        viewModel.showReviewsNumLiveData.observe(viewLifecycleOwner) { reviewsNumLiveData ->
            numOfReviews = reviewsNumLiveData
            binding.reviewsText.text = getString(R.string.rating_bar_text, numOfReviews, rating)
        }
        binding.reviewsText.text = getString(R.string.rating_bar_text, numOfReviews, rating)
    }

    private fun initReviewsLiveDataObserver(reviewViewModel: ReviewViewModel, viewModel: ShowDetailsViewModel) {
        reviewViewModel.reviewsDbLiveData.observe(viewLifecycleOwner) { reviewsLiveData ->
            reviews = reviewsLiveData
            reviews = reviews.sortedArrayWith(compareByDescending{it.id}) as Array<Review>
            adapter.updateReviews(reviews)
            viewModel.loadShowDetails(args.id)
            showReviews()
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
        val drawable = CircularProgressDrawable(binding.showMenuImage.context)
        drawable.setColorSchemeColors(R.color.shows_purple)
        drawable.centerRadius = 100f
        drawable.strokeWidth = 5f
        drawable.start()
        binding.toolbarLayout.title = show.title
        Glide.with(binding.showMenuImage.context)
            .load(show.img_url)
            .placeholder(drawable)
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
