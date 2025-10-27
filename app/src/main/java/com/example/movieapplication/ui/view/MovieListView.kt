package com.example.movieapplication.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapplication.ui.route.AppView
import com.example.movieapplication.ui.viewmodel.MovieListViewModel

@Composable
fun MovieListView(
    viewModel: MovieListViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
){
    val movies by viewModel.movies.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(movies, key = {it.title}) {movie ->
            MovieCard(
                movie = movie,
                onToggleLike = {viewModel.toggleIsLiked(movie)},
                onCardClick = {navController.navigate("${AppView.MovieDetail.name}/${movie.id}")}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MovieListPreview(){
    MovieListView()
}