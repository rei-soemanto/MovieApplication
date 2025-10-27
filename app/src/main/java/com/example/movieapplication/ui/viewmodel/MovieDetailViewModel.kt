package com.example.movieapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.data.container.MovieServerContainer
import com.example.movieapplication.ui.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel: ViewModel() {
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    fun getMovie(id: Int){
        viewModelScope.launch {
            _movie.value = MovieServerContainer().movieServerRepository.GetMovieById(id)
        }
    }

    fun toggleIsLiked() {
        viewModelScope.launch {
            if (movie.value!!.isLiked) {
                MovieServerContainer().movieServerRepository.UnlikeMovie(movie.value!!.id)
            } else {
                MovieServerContainer().movieServerRepository.LikeMovie(movie.value!!.id)
            }
            getMovie(movie.value!!.id)
        }
    }
}