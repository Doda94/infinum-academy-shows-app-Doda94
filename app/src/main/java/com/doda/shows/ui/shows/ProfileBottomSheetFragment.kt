package com.doda.shows.ui.shows

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.doda.shows.databinding.FragmentProfileBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val REMEMBER_ME = "REMEMBER_ME"
private const val USERNAME_SHARED_PREFERENCES = "USERNAME"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class ProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileBottomSheetBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<ProfileBottomSheetFragmentArgs>()

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var uri: Uri

    private val cameraPermissionContract = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isAccepted ->
        if (!isAccepted) {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photo = result.data?.extras?.get("data") as? Bitmap
                photo?.let {
                    Glide
                        .with(requireContext())
                        .load(photo)
                        .into(binding.profilePictureBottomSheet)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun checkPermissionAndOpenCamera(){
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_DENIED
        ) {
            openCamera()
        } else {
            cameraPermissionContract.launch(android.Manifest.permission.CAMERA)
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ){
                openCamera()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileNameBottomSheet.text = args.username

        binding.changeProfilePictureButton.setOnClickListener {
            checkPermissionAndOpenCamera()
        }

        binding.logoutButtonBottomSheet.setOnClickListener {
            sharedPreferences = requireContext().getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            sharedPreferences.edit {
                putBoolean(REMEMBER_ME, false)
                remove(USERNAME_SHARED_PREFERENCES)
            }
            val directions = ProfileBottomSheetFragmentDirections.actionProfileBottomSheetFragmentToMain()
            findNavController().navigate(directions)
        }

    }

}
