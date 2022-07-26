package com.doda.shows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doda.shows.databinding.FragmentProfileBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileBottomSheetBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<ProfileBottomSheetFragmentArgs>()

    private val requestImageCapture = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileNameBottomSheet.text = args.username

        binding.changeProfilePictureButton.setOnClickListener {
        
        }

        binding.logoutButtonBottomSheet.setOnClickListener {
            val directions = ProfileBottomSheetFragmentDirections.actionProfileBottomSheetFragmentToMain()
            findNavController().navigate(directions)
        }

    }

}
