package com.example.moviesapi.ui.screens.movie.movieDiscover

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult
import com.example.moviesapi.ui.screens.common.PopularMovieResultListItem
import com.example.moviesapi.ui.screens.common.ShowLoadingIndicator
import com.example.moviesapi.ui.screens.movie.popularMovies.MoviesPopularViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDiscoverScreen(
    moviesDiscoverViewModel: MovieDiscoverViewModel,
    modifier: Modifier = Modifier,
    onMovieClick: (movieId: Int) -> Unit
) {
    var trendingMovies by moviesDiscoverViewModel.trendingMovies
    val loading by moviesDiscoverViewModel.loading
    var searchQuery by remember { mutableStateOf("") }

    var searchMovies by moviesDiscoverViewModel.moviesSearch

    if (loading) {
        ShowLoadingIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 65.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                val focusManager = LocalFocusManager.current
                DockedSearchBar(
                    query = searchQuery,
                    onQueryChange = { newQuery ->
                        searchQuery = newQuery
                        moviesDiscoverViewModel.getSearchMovie(newQuery, false, "en-US", 1)
                    },
                    onSearch = {
                        focusManager.clearFocus()
                        moviesDiscoverViewModel.getTrendingMovies(1)
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = Color.Transparent
                    ),
                    placeholder = { Text("Search...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .border(
                            1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier
                                .padding(16.dp, top = 5.dp)
                                .size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                modifier = Modifier
                                    .clickable {
                                        searchQuery = ""
                                    }
                                    .padding(16.dp, top = 5.dp)
                                    .size(20.dp)
                            )
                        }
                    },
                    active = true,
                    onActiveChange = { }
                ) { }
            }
            LazyVerticalGrid(
                contentPadding = PaddingValues(2.dp),
                columns = GridCells.Adaptive(100.dp),
                modifier = modifier
                    .padding(top = 10.dp)
            )
            {

                if (searchQuery != "") {
                    items(searchMovies) { popularMovieResult ->
                        PopularMovieResultListItem(
                            popularMovieResult = popularMovieResult,
                            onClick = { onMovieClick(popularMovieResult.id) },
                            //Padding entre las cajas
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                } else {
                    items(trendingMovies) { popularMovieResult ->
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
    }
    LaunchedEffect(trendingMovies) {
        moviesDiscoverViewModel.getTrendingMovies(1)
    }
}
