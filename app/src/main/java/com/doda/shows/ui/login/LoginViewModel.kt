package com.doda.shows.ui.login

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val USER_EMAIL = "USER_EMAIL"
private const val CLIENT = "CLIENT"
private const val IMAGE_URL = "IMAGE_URL"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class LoginViewModel : ViewModel() {

    private val loginResultLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun getLoginResultLiveData(): LiveData<Boolean> {
        return loginResultLiveData
    }

    fun onLoginButtonClicked(email: String, password: String, sharedPreferences: SharedPreferences) {
        val loginRequest = LoginRequest(email, password)
        ApiModule.retrofit.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                loginResultLiveData.value = response.isSuccessful
                val body = response.body()
                sharedPreferences.edit {
                    putString(ACCESS_TOKEN, response.headers()["access-token"])
                    putString(CLIENT, response.headers()["client"])
                    putString(USER_EMAIL, response.headers()["uid"])
                    putString(IMAGE_URL, body?.user?.imageUrl)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResultLiveData.value = false
            }

        })
    }


}