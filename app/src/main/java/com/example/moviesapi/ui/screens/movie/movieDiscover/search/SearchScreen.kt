package com.example.moviesapi.ui.screens.movie.movieDiscover.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun SearchScreen(viewModel: NewSearchViewModel) {

    val viewState = viewModel.viewState.collectAsState().value
    val searchFieldState = viewModel.searchFieldState.collectAsState().value
    val inputText = viewModel.inputText.collectAsState().value

    SearchScreenLayout(
        viewState = viewState,
        searchFieldState = searchFieldState,
        inputText = inputText,
        onSearchInputChanged = { input -> viewModel.updateInput(input) },
        onSearchInputClicked = { viewModel.searchFieldActivated() },
        onClearInputClicked = { viewModel.clearInput() },
        onChevronClicked = { viewModel.revertToInitialState() },
        onItemClicked = {}
    )
}