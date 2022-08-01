package com.doda.shows.ui.shows

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.ApiModule
import com.doda.shows.Show
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doda.shows.FileUtil
import com.doda.shows.databinding.FragmentShowsBinding

private const val PP_CHANGE_KEY = "ppChangeKey"
private const val PP_CHANGE = "ppChange"

class ShowsFragment : Fragment() {

    private lateinit var shows: Array<Show>

    private var _binding: FragmentShowsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ShowsAdapter

    private val viewModel by viewModels<ShowsViewModel>()

    private val args by navArgs<ShowsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showsliveData.observe(viewLifecycleOwner) { showsLiveData ->
            shows = showsLiveData
            initShowsRecycler()
        }

        ApiModule.initRetrofit(requireContext())

        initLoadShowsButton()
        initProfileBottomSheetButton()
//        initFragmentResultListener()

    }

//    private fun initFragmentResultListener() {
//        setFragmentResultListener(PP_CHANGE_KEY) { _, bundle ->
//            val ppChange = bundle.getBoolean(PP_CHANGE)
//            if (ppChange) {
//                loadAvatar(binding.profileBottomSheet)
//            }
//        }
//    }

    private fun initProfileBottomSheetButton() {
        binding.profileBottomSheet.setOnClickListener {
            val directions = ShowsFragmentDirections.actionShowsFragmentToProfileBottomSheetFragment2(args.username)
            findNavController().navigate(directions)
        }
    }

    private fun loadAvatar(imageView: ImageView) {
        Glide
            .with(requireContext())
            .load(FileUtil.getImageFile(requireContext()))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)

    }

    private fun initShowsRecycler() {
        adapter = ShowsAdapter(shows) { show ->
            val directions = ShowsFragmentDirections.actionShowsFragmentToShowDetailsFragment(show.id)
            findNavController().navigate(directions)
        }
        binding.showsRecyclerView.layoutManager = LinearLayoutManager(activity)

        binding.showsRecyclerView.adapter = adapter
    }

    private fun initLoadShowsButton() {
        binding.loadShowsButton.setOnClickListener {
            viewModel.onGetShowsButtonClicked()
            adapter.loadShows(shows)
            binding.showsRecyclerView.isVisible = true
            binding.loadShowsButton.isVisible = false
            binding.loadShowsText.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}