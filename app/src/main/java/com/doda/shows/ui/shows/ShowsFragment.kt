package com.doda.shows.ui.shows

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doda.shows.ApiModule
import com.doda.shows.R
import com.doda.shows.Show
import com.doda.shows.ShowsApplication
import com.doda.shows.ShowsViewModelFactory
import com.doda.shows.UserViewModel
import com.doda.shows.databinding.FragmentShowsBinding
import com.doda.shows.db.ShowsDatabase

private const val PP_CHANGE_KEY = "ppChangeKey"
private const val PP_CHANGE = "ppChange"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class ShowsFragment : Fragment() {

    private var shows: Array<Show> = arrayOf()

    private var _binding: FragmentShowsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ShowsAdapter

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: ShowsViewModel by viewModels {
            ShowsViewModelFactory((activity?.application as ShowsApplication).database)
        }

        ApiModule.initRetrofit(requireContext().getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE))
        viewModel.onGetShowsButtonClicked()
        viewModel.updateDBLiveData()
        binding.toolbar.setTitle(getString(R.string.app_name))
        loadAvatar()


        initShowsDbLiveDataObserver(viewModel)
        initCheckIfShowsAvailable()
        initProfileBottomSheetButton()
        initFragmentResultListener()

    }

    private fun initShowsDbLiveDataObserver(viewModel: ShowsViewModel){
        viewModel.showsDbLiveData.observe(viewLifecycleOwner) { showsDbLiveData ->
            shows = showsDbLiveData
            initShowsRecycler(shows)
            loadShows(shows)
        }
    }

    private fun initCheckIfShowsAvailable() {
        if (shows.isEmpty()){
            binding.showsRecyclerView.isVisible = false
            binding.loadShowsButton.isVisible = true
            binding.loadShowsText.isVisible = true
        }
    }

    private fun initFragmentResultListener() {
        setFragmentResultListener(PP_CHANGE_KEY) { _, bundle ->
            val ppChange = bundle.getBoolean(PP_CHANGE)
            if (ppChange) {
                loadAvatar()
            }
        }
    }

    private fun initProfileBottomSheetButton() {
        binding.toolbar.binding.profileIcon.setOnClickListener{
            val directions = ShowsFragmentDirections.actionShowsFragmentToProfileBottomSheetFragment2()
            findNavController().navigate(directions)
        }
    }

    private fun loadAvatar() {
        var imgUrl: String? = null
        userViewModel.updateUser(requireContext().getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE))
        userViewModel.imageUrlLiveData.observe(viewLifecycleOwner) { imageUrlLiveData ->
            imgUrl = imageUrlLiveData
            binding.toolbar.setProfilePhoto(imgUrl)
        }
    }

    private fun initShowsRecycler(showsArray: Array<Show>) {
        adapter = ShowsAdapter(showsArray) { show ->
            val directions = ShowsFragmentDirections.actionShowsFragmentToShowDetailsFragment(show.id)
            findNavController().navigate(directions)
        }
        binding.showsRecyclerView.layoutManager = LinearLayoutManager(activity)

        binding.showsRecyclerView.adapter = adapter
    }

    private fun loadShows(showsArray: Array<Show>){
        adapter.loadShows(showsArray)
        binding.showsRecyclerView.isVisible = true
        binding.loadShowsButton.isVisible = false
        binding.loadShowsText.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}