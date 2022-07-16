package com.doda.shows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.ActivityShowsBinding

class ShowsActivity : AppCompatActivity(){

    private lateinit var shows: List<Show>

    private lateinit var binding: ActivityShowsBinding

    private lateinit var adapter: ShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shows= listOf(
            Show("1","TheOffice",getString(R.string.TheOffice_description),R.drawable.ic_office),
            Show("2","Stranger Things",getString(R.string.StrangerThings_description),R.drawable.ic_stranger_things),
        )


        binding = ActivityShowsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initShowsRecycler()
        initLoadShowsButton()

    }

    private fun initShowsRecycler() {
        adapter = ShowsAdapter(shows) { show ->
            // TODO: do on item pressed
        }
        binding.showsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.showsRecyclerView.adapter = adapter
    }

    private fun initLoadShowsButton() {
        binding.loadShowsButton.setOnClickListener {
            adapter.loadShows(shows)
            binding.showsRecyclerView.isVisible = true
            binding.loadShowsLinearLayout.isVisible = false
        }
    }


}