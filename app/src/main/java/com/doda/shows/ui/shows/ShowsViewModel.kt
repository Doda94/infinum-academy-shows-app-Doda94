package com.doda.shows.ui.shows

import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.R
import com.doda.shows.Show
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val USER_EMAIL = "USER_EMAIL"
private const val CLIENT = "CLIENT"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class ShowsViewModel : ViewModel() {

    val shows = arrayOf<Show>()

    private val _showsLiveData = MutableLiveData(
        shows
    )

    val showsliveData: LiveData<Array<Show>> = _showsLiveData

    fun onGetShowsButtonClicked(){
        ApiModule.retrofit.shows().enqueue(object : Callback<ShowsResponse>{
            override fun onResponse(call: Call<ShowsResponse>, response: Response<ShowsResponse>) {
                if (response.isSuccessful){
                    val body = response.body()
                }
            }

            override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                return
            }

        })
    }
}