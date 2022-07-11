package com.doda.shows

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.doda.shows.databinding.ActivityLoginBinding
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // needs a rewrite with better button behaviour
        binding.loginButton.setOnClickListener(){
            var email = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            if (TextUtils.isEmpty(email)){
                binding.emailEditText.setError(getString(R.string.email_error))
            }
            if (password.length < 6){
                binding.passwordEditText.setError(getString(R.string.password_error))
            }
            if (!TextUtils.isEmpty(email) && password.length>=6){
                val intent = Intent (this,WelcomeActivitiy::class.java)
                startActivity(intent)
            }
        }


    }
}