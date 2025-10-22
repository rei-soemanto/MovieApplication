package com.example.movieapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movieapplication.ui.model.DummyMovieData
import com.example.movieapplication.ui.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieDetailViewModel: ViewModel() {
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    fun getMovie(title: String){
        _movie.value = DummyMovieData.movies.find { it.title == title }
    }

    fun toggleIsLiked() {
        val current = _movie.value ?: return
        val index = DummyMovieData.movies.indexOfFirst { it.title == current.title }
        if (index == -1) {
            val updated = current.copy(isLiked = !current.isLiked)
            DummyMovieData.movies[index] = updated
            _movie.value = updated
        }
    }
}