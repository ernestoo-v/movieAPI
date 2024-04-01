package com.example.moviesapi.ui.screens.mainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesapi.ui.navigation.MainAppBar
import com.example.moviesapi.ui.theme.MoviesAPITheme


@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel,
    pageNumber: Int,
    onPageClick: (pageNumber: Int) -> Unit,
    onMovieClick: (movieId: Int) -> Unit
) {
    MoviesAPITheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(topBar = {
                MainAppBar(
                    onPageClick = onPageClick,
                    pageNumber = pageNumber,
                )
            }) { padding ->
                PopularMovieResultList(
                    mainScreenViewModel = mainScreenViewModel,
                    modifier = Modifier
                        .padding(0.dp),
                    pageNumber = pageNumber,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}
