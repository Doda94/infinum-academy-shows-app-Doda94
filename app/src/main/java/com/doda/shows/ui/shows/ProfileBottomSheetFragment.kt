package com.doda.shows.ui.shows

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doda.shows.ApiModule
import com.doda.shows.BuildConfig
import com.doda.shows.FileUtil
import com.doda.shows.R
import com.doda.shows.UserViewModel
import com.doda.shows.databinding.FragmentProfileBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response

private const val REMEMBER_ME = "REMEMBER_ME"
private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val USER_EMAIL = "USER_EMAIL"
private const val CLIENT = "CLIENT"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"
private const val PP_CHANGE_KEY = "ppChangeKey"
private const val PP_CHANGE = "ppChange"

class ProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileBottomSheetBinding? = null

    private val binding get() = _binding!!

    lateinit var photoFile: File

    private lateinit var sharedPreferences: SharedPreferences

    private val userViewModel by viewModels<UserViewModel>()

    private fun openCamera() {
        photoFile = FileUtil.createImageFile(requireContext())!!
        FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile)
        photoFile.let {
            val fileUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", it)
            resultLauncher.launch(fileUri)
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                val file = FileUtil.getImageFile(requireContext())
                val request = file?.asRequestBody("multipart/form-data".toMediaType())
                ApiModule.retrofit.profilePhotoUpload(MultipartBody.Part.createFormData("image", file?.name, request!!))
                    .enqueue(object : retrofit2.Callback<ProfilePhotoUploadResponse> {
                        override fun onResponse(call: Call<ProfilePhotoUploadResponse>, response: Response<ProfilePhotoUploadResponse>) {
                            if (response.isSuccessful) {
                                setFragmentResult(PP_CHANGE_KEY, bundleOf(PP_CHANGE to true))
                                findNavController().popBackStack()
                            }
                        }

                        override fun onFailure(call: Call<ProfilePhotoUploadResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        binding.profileNameBottomSheet.text = sharedPreferences.getString(USER_EMAIL, null)

        loadAvatar()

        binding.changeProfilePictureButton.setOnClickListener {
            openCamera()
        }

        binding.logoutButtonBottomSheet.setOnClickListener {
            sharedPreferences.edit {
                putBoolean(REMEMBER_ME, false)
                remove(LOGIN_SHARED_PREFERENCES)
            }
            val directions = ProfileBottomSheetFragmentDirections.actionProfileBottomSheetFragmentToMain()
            findNavController().navigate(directions)
        }

    }

    private fun loadAvatar() {
        var imgUrl: String? = null
        userViewModel.updateUser(requireContext())
        userViewModel.imageUrlLiveData.observe(viewLifecycleOwner) { imageUrlLiveData ->
            imgUrl = imageUrlLiveData
            if (imgUrl != null) {
                Glide
                    .with(requireContext())
                    .load(imgUrl)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.profilePictureBottomSheet)
            } else {
                Glide
                    .with(requireContext())
                    .load(R.drawable.ic_profile_placeholder)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.profilePictureBottomSheet)
            }
        }
    }

}
