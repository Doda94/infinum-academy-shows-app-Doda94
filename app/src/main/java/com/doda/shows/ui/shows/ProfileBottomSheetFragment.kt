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
import com.doda.shows.ApiModule
import com.doda.shows.BuildConfig
import com.doda.shows.FileUtil
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

    private fun openCamera() {
        photoFile = FileUtil.createImageFile(requireContext())!!
        photoFile?.let {
            val fileUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", it)
            resultLauncher.launch(fileUri)
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                val request = photoFile.asRequestBody("multipart/form-data".toMediaType())
                ApiModule.retrofit.profilePhotoUpload(MultipartBody.Part.createFormData("avatar", "avatar.jpg", request)).enqueue(object : retrofit2.Callback<ProfilePhotoUploadResponse>{
                    override fun onResponse(call: Call<ProfilePhotoUploadResponse>, response: Response<ProfilePhotoUploadResponse>) {
                        if (!response.isSuccessful){

                        }
                    }

                    override fun onFailure(call: Call<ProfilePhotoUploadResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
                setFragmentResult(PP_CHANGE_KEY, bundleOf(PP_CHANGE to true))
                findNavController().popBackStack()
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

}
