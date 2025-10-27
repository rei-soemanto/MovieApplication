package com.example.movieapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.data.container.MovieServerContainer
import com.example.movieapplication.data.repository.MovieServerRepositories
import com.example.movieapplication.ui.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieListViewModel: ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        loadMovies()
    }

    fun loadMovies() {
//        _movies.value = DummyMovieData.movies.toList()
        viewModelScope.launch {
            _movies.value = MovieServerContainer().movieServerRepository.getAllMovies().toList()
        }
    }

    fun toggleIsLiked(movie: Movie) {
        viewModelScope.launch {
            if (movie.isLiked){
                MovieServerContainer().movieServerRepository.UnlikeMovie(movie.id)
            }else{
                MovieServerContainer().movieServerRepository.LikeMovie(movie.id)
            }
            loadMovies()
        }
    }
}