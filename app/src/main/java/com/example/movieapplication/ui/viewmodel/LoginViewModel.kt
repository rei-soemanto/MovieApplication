package com.example.movieapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.movieapplication.data.container.MovieServerContainer
import com.example.movieapplication.ui.route.AppView
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    fun submit(
        email: String,
        password: String,
        navController: NavController,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            val token = MovieServerContainer().movieServerRepository.Login(email, password)
            onResult(token)
            MovieServerContainer.ACCESS_TOKEN = token
            navController.navigate(AppView.MovieList.name) {
                popUpTo(AppView.Login.name) { inclusive = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}