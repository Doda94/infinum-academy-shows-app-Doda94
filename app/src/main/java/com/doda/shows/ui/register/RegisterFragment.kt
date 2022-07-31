package com.doda.shows.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doda.shows.ApiModule
import com.doda.shows.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiModule.initRetrofit()



        viewModel.getRegistrationResultLiveData().observe(viewLifecycleOwner){ registrationSuccessful ->
            if (registrationSuccessful){
                findNavController().popBackStack()
            }else{
                Toast.makeText(requireContext(), "Registration not successful", Toast.LENGTH_SHORT).show()
            }

        }
        initRegisterButtonListener()
    }

    private fun initRegisterButtonListener() {
        binding.registerButton.setOnClickListener{
            viewModel.onRegisterButtonClicked(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.confrimPasswordEditText.text.toString()
            )
        }
    }

}