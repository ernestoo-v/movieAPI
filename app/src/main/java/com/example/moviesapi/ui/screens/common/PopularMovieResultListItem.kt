package com.example.moviesapi.ui.screens.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult

@Composable
fun PopularMovieResultListItem(
    popularMovieResult: PopularMovieResult,
    onClick: () -> Unit,
    modifier: Modifier
) {
    CharacterThumb(popularMovieResult, onClick)
}
@Composable
fun CharacterThumb(item: PopularMovieResult, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500/${item.poster_path}")
                .crossfade(500)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}
