package com.doda.shows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doda.shows.databinding.ActivityWelcomeBinding

class WelcomeActivitiy : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val welcomeMessage = R.string.welcome.toString() + ", " + username + "!"

        binding.welcomeText.setText(welcomeMessage)


    }
}