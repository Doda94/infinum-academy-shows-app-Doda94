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

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val USER_EMAIL = "USER_EMAIL"
private const val CLIENT = "CLIENT"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

class ShowsViewModel : ViewModel() {

    private val MEDIA_TYPE_JPG = "image/jpg".toMediaType()

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

    fun changeProfilePhoto(context: Context, file: File?) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
        val uid = sharedPreferences.getString(USER_EMAIL, null)
        val client = sharedPreferences.getString(CLIENT, null)
        if (file != null) {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "avatar.jpg", file.asRequestBody(MEDIA_TYPE_JPG))
                .build()

            val request = Request.Builder()
                .header("token-type", "Bearer")
                .header("access-token", accessToken.toString())
                .header("uid", uid.toString())
                .header("client", client.toString())
                .post(requestBody)
                .build()

            val client = OkHttpClient()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful){

                }
                else{

                }
            }

        }
    }
}