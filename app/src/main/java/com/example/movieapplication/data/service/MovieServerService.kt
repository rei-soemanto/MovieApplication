package com.example.movieapplication.data.service

import com.example.movieapplication.data.dto.RequestLogin
import com.example.movieapplication.data.dto.RequestRegister
import com.example.movieapplication.data.dto.ResponseMovie
import com.example.movieapplication.data.dto.ResponseMovieList
import com.example.movieapplication.data.dto.ResponseStatusLike
import com.example.movieapplication.data.dto.ResponseToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieServerService {
    @POST("api/auth/register")
    suspend fun Register(@Body request: RequestRegister): Response<ResponseToken>

    @POST("api/auth/login")
    suspend fun Login(@Body login: RequestLogin): Response<ResponseToken>

    @GET("api/movies")
    suspend fun GetMovies(): Response<ResponseMovieList>

    @GET("api/movies/{id}")
    suspend fun GetMovieById(@Path("id") id: Int): Response<ResponseMovie>

    @GET("api/movies/{id}/like")
    suspend fun CheckLike(@Path("id") id: Int): Response<ResponseStatusLike>

    @POST("api/movies/{id}/like")
    suspend fun Like(@Path("id") id: Int): Response<Unit>

    @POST("api/movies/{id}/like")
    suspend fun Unlike(@Path("id") id: Int): Response<Unit>
}