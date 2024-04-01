package com.example.moviesapi

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.moviesapi.ui.navigation.Navigation
import com.example.moviesapi.ui.screens.movie.movieDiscover.MovieDiscoverViewModel
import com.example.moviesapi.ui.screens.movie.popularMovies.MoviesPopularViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(
                moviesPopularViewModel = MoviesPopularViewModel(),
                movieDiscoverViewModel = MovieDiscoverViewModel()
            )
        }
    }
}
