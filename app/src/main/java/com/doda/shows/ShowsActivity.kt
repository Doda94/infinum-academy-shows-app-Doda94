package com.doda.shows

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.doda.shows.databinding.ActivityShowsBinding

class ShowsActivity : AppCompatActivity() {

    private lateinit var shows: List<Show>

    private lateinit var binding: ActivityShowsBinding

    private lateinit var adapter: ShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shows = listOf(
            Show("0", "TheOffice", getString(R.string.TheOffice_description), R.drawable.ic_office),
            Show("1", "Stranger Things", getString(R.string.StrangerThings_description), R.drawable.ic_stranger_things),
        )


        binding = ActivityShowsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initShowsRecycler()
        initLoadShowsButton()

    }

    private fun initShowsRecycler() {
        adapter = ShowsAdapter(shows) { show ->
            val intent1 = Intent(this, ShowDetailsActivity::class.java)
            intent1.putExtra("show_name", adapter.getShowName(show))
            intent1.putExtra("show_desc", adapter.getShowDesc(show))
            intent1.putExtra("show_img_id", adapter.getShowImg(show))
            intent1.putExtra("username", intent.getStringExtra("username"))
            startActivity(intent1)
        }

        binding.showsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.showsRecyclerView.adapter = adapter
    }

    private fun initLoadShowsButton() {
        binding.loadShowsButton.setOnClickListener {
            adapter.loadShows(shows)
            binding.showsRecyclerView.isVisible = true
            binding.loadShowsButton.isVisible = false
            binding.loadShowsText.isVisible = false
        }
    }


}