package com.example.movieapplication.ui.model

data class Movie (
    val id: Int = -1,
    val title: String,
    val description: String,
    val rating: Double,
    val genre: String,
    val director: String,
    val releaseYear: Int,
    val posterPath: String = "",
    val posterResId: Int = 0,
    val isLiked: Boolean = false
)