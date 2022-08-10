package com.doda.shows.ui.showdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doda.shows.R
import com.doda.shows.Review
import com.doda.shows.ShowsApplication
import com.doda.shows.databinding.FragmentAddReviewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.concurrent.Executors

private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class AddReviewBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddReviewBottomSheetBinding? = null

    private val binding get() = _binding!!

    var lastId = "0"

    private val args by navArgs<AddReviewBottomSheetFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddReviewBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val viewModel: ReviewViewModel by viewModels {
            ReviewViewModelFactory((activity?.application as ShowsApplication).database)
        }

        binding.closeBottomSheetButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val database = (activity?.application as ShowsApplication).database

        database.showsDAO().getShowReviews(args.showId.toString()).observe(viewLifecycleOwner) { reviews ->
            val sortedReviews = reviews.sortedArrayWith(compareByDescending { it.id }) as Array<Review>
            if (sortedReviews.isNotEmpty()) {
                lastId = sortedReviews[0].id
            }
        }

        binding.submitReviewButton.setOnClickListener {
            val rating = binding.writeRatingBar.rating.toInt()
            val comment = binding.reviewTextInputEditText.text.toString()
            if (rating == 0) {
                binding.submitReviewButton.error = getString(R.string.rating_error)
            } else {
                viewModel.addReview(rating, comment, args.showId, sharedPreferences, lastId)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}