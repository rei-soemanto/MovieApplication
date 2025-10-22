package com.example.movieapplication.data.repository

import com.example.movieapplication.data.dto.RequestLogin
import com.example.movieapplication.data.dto.RequestRegister
import com.example.movieapplication.data.service.MovieServerService
import com.example.movieapplication.ui.model.Movie

class MovieServerRepositories(private val service: MovieServerService) {
    suspend fun getAllMovies(): List<Movie> {
        val movies = service.GetMovies().body()!!

        val dataMovieListView = mutableListOf<Movie>()

        for (movie in movies) {
            val isLiked = service.CheckLike(movie.id).body()!!.liked
            val movieItem = Movie(
                id = movie.id,
                title = movie.title,
                description = movie.description,
                rating = movie.rating,
                genre = movie.genre,
                director = movie.director,
                releaseYear = movie.releaseYear,
                posterPath = movie.posterPath,
                posterResId = 0,
                isLiked = isLiked
            )
            dataMovieListView.add(movieItem)
        }
        return dataMovieListView
    }

    suspend fun Login(email: String, password: String): String{
        val data = RequestLogin(email, password)
        val serverToken = service.Login(data).body()!!
        return serverToken.token
    }

    suspend fun Register(name: String, email: String, password: String): String{
        val data = RequestRegister(email, name, password)
        val serverToken = service.Register(data).body()!!
        return serverToken.token
    }

    suspend fun GetMovieById(id: Int): Movie {
        val movieItem = service.GetMovieById(id).body()!!
        val isLiked = service.CheckLike(movieItem.id).body()!!.liked
        val movie = Movie(
            id = movieItem.id,
            title = movieItem.title,
            description = movieItem.description,
            rating = movieItem.rating,
            genre = movieItem.genre,
            director = movieItem.director,
            releaseYear = movieItem.releaseYear,
            posterPath = movieItem.posterPath,
            isLiked = isLiked
        )
        return movie
    }

    suspend fun LikeMovie(id: Int): Boolean{
        return try {
            val res = service.Like(id)
            res.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun UnlikeMovie(id: Int): Boolean{
        return try {
            val res = service.Unlike(id)
            res.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}