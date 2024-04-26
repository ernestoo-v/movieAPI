package com.example.moviesapi.ui.screens.mainScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesapi.stateTutorial.WellnessScreen
import com.example.moviesapi.ui.navigation.CustomTopAppBar
import com.example.moviesapi.ui.navigation.MainAppBar
import com.example.moviesapi.ui.navigation.navigationBar.NavigationBarMolecule
import com.example.moviesapi.ui.screens.common.TabBarItems.tabBarItems
import com.example.moviesapi.ui.screens.movie.movieDiscover.MovieDiscoverScreen
import com.example.moviesapi.ui.screens.movie.movieDiscover.MovieDiscoverViewModel
import com.example.moviesapi.ui.screens.movie.movieDiscover.search.NewSearchViewModel
import com.example.moviesapi.ui.screens.movie.movieDiscover.search.SearchScreen
import com.example.moviesapi.ui.screens.movie.popularMovies.MoviesPopularScreen
import com.example.moviesapi.ui.screens.movie.popularMovies.MoviesPopularViewModel
import com.example.moviesapi.ui.theme.MoviesAPITheme

@Composable
fun MainScreen(
    moviesPopularViewModel: MoviesPopularViewModel,
    movieDiscoverViewModel: MovieDiscoverViewModel,
    pageNumber: Int,
    onPageClick: (pageNumber: Int) -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
    onBottomAppBarClick: (titleRoute: String, selectedTabIndexL: Int) -> Unit,
    selectedTabIndex: Int,
    onUpClick: () -> Unit,
) {
    MoviesAPITheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(topBar = {
                when (selectedTabIndex) {
                    0 -> {
                        MainAppBar(
                            onPageClick = onPageClick,
                            pageNumber = pageNumber,
                        )
                    }

                    1 -> {
                        CustomTopAppBar(
                            title = "Discover",
                            onUpClick = onUpClick
                        )
                    }

                    2 -> {
                        CustomTopAppBar(
                            title = "Wellness",
                            onUpClick = onUpClick
                        )
                    }
                }
            },
                bottomBar = {
                    NavigationBarMolecule(
                        tabBarItems = tabBarItems,
                        onBottomAppBarClick = onBottomAppBarClick,
                        selectedTabIndex = selectedTabIndex
                    )
                }
            ) { padding ->
                Log.d("Revisar", "tabNumber -> $selectedTabIndex")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 85.dp)
                ) {
                    when (selectedTabIndex) {
                        0 -> {
                            SearchScreen(viewModel = NewSearchViewModel())
                            /* MoviesPopularScreen(
                                moviesPopularViewModel = moviesPopularViewModel,
                                modifier = Modifier
                                    .padding(0.dp),
                                pageNumber = pageNumber,
                                onMovieClick = onMovieClick
                            )*/
                            Log.d("Revisar", "Dentro del 0")
                        }

                        1 -> {
                            MovieDiscoverScreen(
                                moviesDiscoverViewModel = movieDiscoverViewModel,
                                modifier = Modifier
                                    .padding(0.dp),
                                onMovieClick = onMovieClick
                            )
                            Log.d("Revisar", "Dentro del 1")
                        }

                        2 -> {
                            WellnessScreen()
                            Log.d("Revisar", "Dentro del 2")
                        }
                    }
                }

            }
        }
    }
}
