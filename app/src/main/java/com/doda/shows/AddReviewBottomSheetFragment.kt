package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.doda.shows.databinding.FragmentAddReviewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val REVIEW_KEY = "reviewKey"
private const val COMMENT = "comment"
private const val RATING = "rating"

class AddReviewBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddReviewBottomSheetBinding? = null

    private val binding get() = _binding!!

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
                val bundle = Bundle()
                bundle.putString(COMMENT, comment)
                bundle.putInt(RATING, rating)
                setFragmentResult(REVIEW_KEY, bundle)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}