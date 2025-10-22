package com.example.movieapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movieapplication.ui.model.DummyMovieData
import com.example.movieapplication.ui.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieListViewModel: ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        loadMovies()
    }

    fun loadMovies() {
        _movies.value = DummyMovieData.movies.toList()
    }

    fun toggleIsLiked(movie: Movie) {
        val index = DummyMovieData.movies.indexOfFirst { it.title == movie.title && it.releaseYear == movie.releaseYear }
        if (index == -1) return

        val updated = DummyMovieData.movies[index].copy(
            isLiked = !DummyMovieData.movies[index].isLiked
        )
        DummyMovieData.movies[index] = updated

        _movies.value = DummyMovieData.movies.toList()

    }
}