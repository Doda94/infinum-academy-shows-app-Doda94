package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.doda.shows.databinding.FragmentAddReviewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddReviewBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddReviewBottomSheetBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ReviewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddReviewBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.closeBottomSheetButton.setOnClickListener{
            onDestroyView()
        }
        
        binding.submitReviewButton.setOnClickListener{
            if (binding.writeRatingBar.rating.toInt() == 0){
                binding.submitReviewButton.setError(getString(R.string.rating_error))
            }
            else{
                val directions = AddReviewBottomSheetFragmentDirections.actionAddReviewBottomSheetFragmentToShowDetailsFragment()
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}