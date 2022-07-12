package com.doda.shows

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.doda.shows.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isEmailValid(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    var email_valid = false
    var password_valid = false

    fun disableButton(button: Button) {
        button.isEnabled = false
        button.alpha = 0.5F
    }

    fun enableButton(button: Button) {
        button.isEnabled = true
        button.alpha = 1F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        disableButton(binding.loginButton)

        binding.emailEditText.doOnTextChanged { text, start, before, count ->
            email_valid = isEmailValid(binding.emailEditText.text.toString())
            if (email_valid && password_valid) {
                enableButton(binding.loginButton)
            } else {
                disableButton(binding.loginButton)
            }
            if (!email_valid) {
                binding.emailEditText.setError(getString(R.string.email_error))
            }
        }

        binding.passwordEditText.doOnTextChanged { text, start, before, count ->
            password_valid = binding.passwordEditText.text.toString().length >= 6
            if (email_valid && password_valid) {
                enableButton(binding.loginButton)
            } else {
                disableButton(binding.loginButton)
            }
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, WelcomeActivitiy::class.java)
            intent.putExtra("username", binding.emailEditText.text.toString().substringBefore("@"))
            startActivity(intent)
        }


    }
}