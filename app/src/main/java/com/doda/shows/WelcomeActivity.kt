package com.doda.shows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doda.shows.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val welcomeMessage = getString(R.string.welcome) + ", " + username + "!"

        binding.welcomeText.text = welcomeMessage

    }
}