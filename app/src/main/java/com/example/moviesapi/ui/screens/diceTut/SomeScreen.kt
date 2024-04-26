package com.example.moviesapi.ui.screens.diceTut

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeV = viewModel()
) {
    val uiState: SomeUiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    // Emit UI given uiState. SomeScreen will recompose
    // whenever `viewModel.uiState` emits a new value.
}