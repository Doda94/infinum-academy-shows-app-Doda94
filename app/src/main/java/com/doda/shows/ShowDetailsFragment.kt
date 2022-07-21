package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.FragmentShowDetailsBinding

class ShowDetailsFragment : Fragment() {

    private lateinit var reviews: List<Review>

    private var _binding: FragmentShowDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ReviewsAdapter

    private val args by navArgs<ShowDetailsNestedGraphArgs>()

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
            openReviewBottomSheet()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val navBackStackEntry = findNavController().getBackStackEntry(R.id.showDetailsFragment)

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains("rating")) {
                val rating = navBackStackEntry.savedStateHandle.get<Int>("rating")
                var comment = navBackStackEntry.savedStateHandle.get<String>("comment")
                if (rating != null) {
                    if (comment == null) {
                        comment = ""
                    }
                    adapter.addReview(Review(args.username, rating.toInt(), comment))
                    findNavController().currentBackStackEntry?.savedStateHandle?.remove<String>("comment")
                    findNavController().currentBackStackEntry?.savedStateHandle?.remove<Int>("rating")
                }
                updateRatingBar()
                showReviews()
            }
        }

        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
