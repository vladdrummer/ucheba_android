package com.vladdrummer.uchebaandroid.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vladdrummer.uchebaandroid.data.api.BasicAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitServiceGenerator {

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .protocols((listOf(Protocol.HTTP_1_1)))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(BasicAuthInterceptor("login@login.com","pass"))
            .build()

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)


    fun <S> createService(serviceClass: Class<S>, url: String): S {
        return retrofitBuilder.baseUrl(url).build().create(serviceClass)
    }
}