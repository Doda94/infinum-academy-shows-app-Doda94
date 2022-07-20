package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doda.shows.databinding.FragmentShowsBinding

class ShowsFragment : Fragment() {

    private lateinit var shows: List<Show>

    private var _binding: FragmentShowsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ShowsAdapter

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

        shows = listOf(
            Show("0", "TheOffice", getString(R.string.TheOffice_description), R.drawable.ic_office),
            Show("1", "Stranger Things", getString(R.string.StrangerThings_description), R.drawable.ic_stranger_things),
        )

        initShowsRecycler()
        initLoadShowsButton()
    }

    private fun initShowsRecycler() {
        adapter = ShowsAdapter(shows) { show ->
            val username = args.username
            val directions = ShowsFragmentDirections.actionShowsFragmentToShowDetailsFragment(
                username,
                adapter.getShowName(show),
                adapter.getShowDesc(show),
                adapter.getShowImg(show)
            )
            findNavController().navigate(directions)
        }

        //                binding.showsRecyclerView.layoutManager = LinearLayoutManager(this)

        //        binding.showsRecyclerView.adapter = adapter
    }

    private fun initLoadShowsButton() {
        binding.loadShowsButton.setOnClickListener {
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