package com.vladdrummer.uchebaandroid.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("login")
    suspend fun login(@Query("name")login: String, @Query("password") pass: String) : Response<Void>
}