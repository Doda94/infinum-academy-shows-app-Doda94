package com.doda.shows.ui.showdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doda.shows.R
import com.doda.shows.databinding.FragmentAddReviewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddReviewBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddReviewBottomSheetBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<ReviewViewModel>()

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

        binding.closeBottomSheetButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.submitReviewButton.setOnClickListener {
            val rating = binding.writeRatingBar.rating.toInt()
            val comment = binding.reviewTextInputEditText.text.toString()
            if (rating == 0) {
                binding.submitReviewButton.error = getString(R.string.rating_error)
            } else {
                viewModel.addReview(rating, comment, args.showId)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}