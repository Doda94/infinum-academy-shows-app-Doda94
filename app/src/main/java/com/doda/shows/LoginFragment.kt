package com.doda.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.doda.shows.databinding.FragmentLoginBinding
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableButton(binding.loginButton)
        initEmailListener()
        initPasswordListener()
        initLoginButtonListener()

    }

    fun initRememberMeListener() {
        binding.rememberMeCheckbox.setOnClickListener{

        }
    }

    fun initLoginButtonListener() {
        binding.loginButton.setOnClickListener {
            val username: String = binding.emailEditText.text.toString().substringBefore("@")
            val directions = LoginFragmentDirections.actionLoginFragmentToShowDetailsNestedGraph(username)
            findNavController().navigate(directions)
        }
    }

    fun initPasswordListener() {
        binding.passwordEditText.doOnTextChanged { text, _, _, _ ->
            passwordValid = text.toString().length >= passwordValidLength
            if (emailValid && passwordValid) {
                enableButton(binding.loginButton)
            } else {
                disableButton(binding.loginButton)
            }
        }
    }

    fun initEmailListener() {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}