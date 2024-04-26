package com.example.moviesapi.ui.screens.movie.movieDiscover.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult

@Composable
fun SearchScreenLayout(
    viewState: NewSearchViewModel.ViewState,
    searchFieldState: NewSearchViewModel.SearchFieldState,
    inputText: String,
    onSearchInputChanged: (String) -> Unit,
    onSearchInputClicked: () -> Unit,
    onClearInputClicked: () -> Unit,
    onChevronClicked: () -> Unit,
    onItemClicked: (PopularMovieResult) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .width(350.dp)
            .height(350.dp)
            .background(Color.LightGray)
    ) {
        //SearchHeader(searchFieldState = searchFieldState)
        SearchInputField(
            searchFieldState = searchFieldState,
            inputText = inputText,
            onClearInputClicked = onClearInputClicked,
            onSearchInputChanged = onSearchInputChanged,
            onClicked = onSearchInputClicked,
            onChevronClicked = onChevronClicked,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Blue.copy(alpha = 0.2f))
        )
        when (viewState) {
            NewSearchViewModel.ViewState.IdleScreen -> {

            }

            NewSearchViewModel.ViewState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error :(", color = Color.Red)
                }
            }

            NewSearchViewModel.ViewState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            NewSearchViewModel.ViewState.NoResults -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No results for this input :(", color = Color.White)
                }
            }

            is NewSearchViewModel.ViewState.SearchResultsFetched -> {
                SearchResultsList(items = viewState.results, onItemClicked = onItemClicked)
            }
        }
    }
}