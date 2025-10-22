package com.example.movieapplication.data.container

import com.example.movieapplication.data.interceptor.AuthInterceptor
import com.example.movieapplication.data.repository.MovieServerRepositories
import com.example.movieapplication.data.service.MovieServerService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class MovieServerContainer {
    companion object{
        val BASE_IMG_URL = ""
        val BASE_URL = ""
        val ACCESS_TOKEN = ""
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(ACCESS_TOKEN))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val retrofitService: MovieServerService by lazy {
        retrofit.create(MovieServerService::class.java)
    }

    val movieServerRepository : MovieServerRepositories by lazy {
        MovieServerRepositories(retrofitService)
    }
}