package com.doda.shows

import com.doda.shows.ui.login.LoginRequest
import com.doda.shows.ui.login.LoginResponse
import com.doda.shows.ui.register.RegisterRequest
import com.doda.shows.ui.register.RegisterResponse
import com.doda.shows.ui.showdetails.ReviewRequest
import com.doda.shows.ui.showdetails.ReviewResponse
import com.doda.shows.ui.showdetails.ReviewsResponse
import com.doda.shows.ui.showdetails.ShowDetailsResponse
import com.doda.shows.ui.shows.ProfilePhotoUploadResponse
import com.doda.shows.ui.shows.ShowsResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ShowsApiService {

    @POST("/users")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/users/sign_in")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/shows")
    fun shows(): Call<ShowsResponse>

    @GET("/shows/{id}")
    fun showDetails(@Path("id") id: String): Call<ShowDetailsResponse>

    @GET("shows/{show_id}/reviews")
    fun reviews(@Path("show_id") id: Int): Call<ReviewsResponse>

    @POST("/reviews")
    fun review(@Body request: ReviewRequest): Call<ReviewResponse>

    @Multipart
    @PUT("/users")
    fun profilePhotoUpload(@Part img: MultipartBody.Part): Call<ProfilePhotoUploadResponse>

    @GET("/users/me")
    fun getUser(): Call<UserResponse>

}
