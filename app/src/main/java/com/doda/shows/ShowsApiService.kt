package com.doda.shows

import com.doda.shows.ui.login.LoginRequest
import com.doda.shows.ui.login.LoginResponse
import com.doda.shows.ui.register.RegisterRequest
import com.doda.shows.ui.register.RegisterResponse
import com.doda.shows.ui.shows.ShowsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShowsApiService {

    @POST("/users")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/users/sign_in")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/shows")
    fun shows(): Call<ShowsResponse>

}
