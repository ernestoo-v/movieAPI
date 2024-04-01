package com.example.moviesapi.ui.screens.movie.popularMovies

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesapi.ui.screens.common.PopularMovieResultListItem
import com.example.moviesapi.ui.screens.common.ShowLoadingIndicator

@Composable
fun MoviesPopularScreen(
    moviesPopularViewModel: MoviesPopularViewModel,
    modifier: Modifier = Modifier,
    pageNumber: Int,
    onMovieClick: (movieId: Int) -> Unit
) {
    val popularMovies by moviesPopularViewModel.popularMovies
    val loading by moviesPopularViewModel.loading

    var pageNumberAppend by remember { mutableStateOf(pageNumber) }
    val scrollState = rememberLazyListState()

    if (loading) {
        ShowLoadingIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 65.dp)
        ) {
            LazyVerticalGrid(
                contentPadding = PaddingValues(2.dp),
                columns = GridCells.Adaptive(100.dp)
            )
            {
                items(popularMovies) { popularMovieResult ->
                    PopularMovieResultListItem(
                        popularMovieResult = popularMovieResult,
                        onClick = {
                            Log.d("Revisar", "Movie click -> ${popularMovieResult.id}")
                            onMovieClick(popularMovieResult.id)
                        },
                        //Padding entre las cajas
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
    LaunchedEffect(moviesPopularViewModel) {
        moviesPopularViewModel.getPopularMovies(pageNumber)
    }
}