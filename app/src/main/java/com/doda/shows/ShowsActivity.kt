package com.doda.shows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.ActivityShowsBinding

class ShowsActivity : AppCompatActivity(){

    private val Shows= listOf(
        Show("TheOffice", R.string.TheOffice_description.toString(),R.drawable.ic_office),
        Show("Stranger Things", R.string.StrangerThings_description.toString(),R.drawable.ic_stranger_things),
    )

    private lateinit var binding: ActivityShowsBinding

    private lateinit var adapter: ShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initShowsRecycler()

    }

    private fun initShowsRecycler() {
        adapter = ShowsAdapter(Shows) { show ->
            // TODO: do on item pressed
        }
        binding.showsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.showsRecyclerView.adapter = adapter

    }


}