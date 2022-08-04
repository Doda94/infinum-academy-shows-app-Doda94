package com.doda.shows

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val USER_EMAIL = "USER_EMAIL"
private const val CLIENT = "CLIENT"
private const val IMAGE_URL = "IMAGE_URL"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class UserViewModel : ViewModel() {

    private lateinit var user: User

    private var _imageUrlLiveData = MutableLiveData("")

    val imageUrlLiveData: LiveData<String> = _imageUrlLiveData

    fun updateUser(sharedPreferences: SharedPreferences) {
        ApiModule.initRetrofit(sharedPreferences)
        ApiModule.retrofit.getUser().enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val body = response.body()
                _imageUrlLiveData.value = body?.user?.imageUrl
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}