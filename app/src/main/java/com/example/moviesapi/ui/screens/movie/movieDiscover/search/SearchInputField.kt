package com.example.moviesapi.ui.screens.movie.movieDiscover.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputField(
    searchFieldState: NewSearchViewModel.SearchFieldState,
    inputText: String,
    onSearchInputChanged: (String) -> Unit,
    onClearInputClicked: () -> Unit,
    onClicked: () -> Unit,
    onChevronClicked: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    DockedSearchBar(
        query = inputText,
        onQueryChange = { newInput ->
            onSearchInputChanged(newInput)
        },
        onSearch = {
            focusManager.clearFocus()
            //moviesDiscoverViewModel.getTrendingMovies(1)
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
            when (searchFieldState) {
                NewSearchViewModel.SearchFieldState.Idle -> Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(16.dp, top = 5.dp)
                        .size(20.dp)
                )
                NewSearchViewModel.SearchFieldState.EmptyActive,
                NewSearchViewModel.SearchFieldState.WithInputActive -> Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(16.dp, top = 5.dp)
                        .size(20.dp)
                        .clickable { onChevronClicked() }
                )
            }
        },
        trailingIcon = {
            if (searchFieldState is NewSearchViewModel.SearchFieldState.WithInputActive) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .clickable {
                            onClearInputClicked()
                        }
                        .padding(16.dp, top = 5.dp)
                        .size(20.dp)
                )
            } else {
                null
            }
        },
        active = true,
        onActiveChange = { }
    ) { }
}