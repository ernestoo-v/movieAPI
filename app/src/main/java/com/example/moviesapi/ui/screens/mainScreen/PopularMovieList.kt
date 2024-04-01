package com.example.moviesapi.ui.screens.mainScreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.moviesapi.ui.screens.common.ShowLoadingIndicator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMovieResultList(
    mainScreenViewModel: MainScreenViewModel,
    modifier: Modifier = Modifier,
    pageNumber: Int,
    onMovieClick: (movieId: Int) -> Unit
) {
    var popularMovies by mainScreenViewModel.popularMovies
    val loading by mainScreenViewModel.loading
    var searchQuery by remember { mutableStateOf("") }

    var searchMovies by mainScreenViewModel.moviesSearch

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
                        mainScreenViewModel.getSearchMovie(newQuery, false, "en-US", 1)
                    },
                    onSearch = {
                        focusManager.clearFocus()
                        mainScreenViewModel.getPopularMovies(pageNumber)
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
                    items(popularMovies) { popularMovieResult ->
                        PopularMovieResultListItem(
                            popularMovieResult = popularMovieResult,
                            onClick = {
                                Log.d("Revisar","Movie click -> ${popularMovieResult.id}")
                                onMovieClick(popularMovieResult.id) },
                            //Padding entre las cajas
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                }
            }

        }

    }
    LaunchedEffect(mainScreenViewModel) {
        mainScreenViewModel.getPopularMovies(pageNumber)
    }
}

/*
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = searchQuery,
        onValueChange = { newQuery ->
            onSearchQueryChange(newQuery)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .background(Color.Green),
        placeholder = { Text("Search...", fontSize = 20.sp,modifier=Modifier.padding(20.dp)) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 20.sp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                focusManager.clearFocus()
            }
        )
    )
}*/


//@Preview(showBackground = true)
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