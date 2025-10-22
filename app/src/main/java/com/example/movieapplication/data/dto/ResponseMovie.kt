package com.example.movieapplication.data.dto

data class ResponseMovie(
    val description: String,
    val director: String,
    val genre: String,
    val id: Int,
    val posterPath: String,
    val posterUrl: String,
    val rating: Double,
    val releaseYear: Int,
    val title: String
)