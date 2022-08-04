package com.doda.shows.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : ViewModel() {

    private val registrationResultLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun getRegistrationResultLiveData(): LiveData<Boolean> {
        return registrationResultLiveData
    }

    fun onRegisterButtonClicked(email: String, password: String, confirmPassword: String) {
        val registerRequest = RegisterRequest(email, password, confirmPassword)
        ApiModule.retrofit.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                registrationResultLiveData.value = response.isSuccessful
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registrationResultLiveData.value = false
            }

        })
    }
}