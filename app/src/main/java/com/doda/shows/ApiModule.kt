package com.doda.shows

import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val USER_EMAIL = "USER_EMAIL"
private const val CLIENT = "CLIENT"
private const val LOGIN_SHARED_PREFERENCES = "LOGIN"

object ApiModule {
    private const val BASE_URL = "https://tv-shows.infinum.academy/"

    lateinit var retrofit: ShowsApiService

    fun initRetrofit(sharedPreferences: SharedPreferences) {
        val okhttp = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
            .addInterceptor(AuthInterceptor(sharedPreferences))
            .build()

        retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .client(okhttp).build().create(ShowsApiService::class.java)
    }

}

class AuthInterceptor (private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
        val uid = sharedPreferences.getString(USER_EMAIL, null)
        val client = sharedPreferences.getString(CLIENT, null)

        var request = chain.request()
        request = request.newBuilder()
            .header("token-type", "Bearer")
            .header("access-token", accessToken.toString())
            .header("uid", uid.toString())
            .header("client", client.toString())
            .build()

        return chain.proceed(request)
    }
}