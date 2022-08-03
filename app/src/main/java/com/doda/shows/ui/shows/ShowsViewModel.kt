package com.doda.shows.ui.shows

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doda.shows.ApiModule
import com.doda.shows.FileUtil
import com.doda.shows.Show
import java.io.File
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsViewModel : ViewModel() {

    var shows = arrayOf<Show>()

    private var _showsLiveData = MutableLiveData(
        shows
    )

    val showsliveData: LiveData<Array<Show>> = _showsLiveData

    fun onGetShowsButtonClicked() {
        ApiModule.retrofit.shows().enqueue(object : Callback<ShowsResponse> {
            override fun onResponse(call: Call<ShowsResponse>, response: Response<ShowsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        shows = body.shows
                        _showsLiveData.value = shows
                    }
                }
            }

            override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                // TODO
            }

        })
    }
}