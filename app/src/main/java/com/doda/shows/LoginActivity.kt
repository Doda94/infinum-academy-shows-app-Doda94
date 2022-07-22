package com.doda.shows

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.doda.shows.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val passwordValidLength = 6

    private val emailAddressPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private fun isEmailValid(str: String): Boolean {
        return emailAddressPattern.matcher(str).matches()
    }

    private var emailValid = false
    private var passwordValid = false

    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.alpha = 0.5F
    }

    private fun enableButton(button: Button) {
        button.isEnabled = true
        button.alpha = 1F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        disableButton(binding.loginButton)

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            emailValid = isEmailValid(text.toString())
            if (emailValid && passwordValid) {
                enableButton(binding.loginButton)
            } else {
                disableButton(binding.loginButton)
            }
            //if (!emailValid) {
                // Breaks scrolling for some reason
                // TODO : fix login button when this enabled
                //binding.emailEditText.error = getString(R.string.email_error)
            //}
        }

        binding.passwordEditText.doOnTextChanged { text, _, _, _ ->
            passwordValid = text.toString().length >= passwordValidLength
            if (emailValid && passwordValid) {
                enableButton(binding.loginButton)
            } else {
                disableButton(binding.loginButton)
            }
        }


        binding.loginButton.setOnClickListener {
            val intent = Intent(this, ShowsActivity::class.java)
            intent.putExtra("username", binding.emailEditText.text.toString().substringBefore("@"))
            startActivity(intent)
        }


    }
}