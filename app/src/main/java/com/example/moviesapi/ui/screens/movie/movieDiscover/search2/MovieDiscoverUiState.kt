package com.example.moviesapi.ui.screens.movie.movieDiscover.search2

import com.example.moviesapi.data.entities.movieLists.PopularMovieResult

data class MovieDiscoverUiState(
    var searchQuery: String = "",
    val trendingMovies: List<PopularMovieResult> = emptyList(),
    val moviesSearch: List<PopularMovieResult> = emptyList(),
    val loading: Boolean = true

)
