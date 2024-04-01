package com.example.moviesapi.data.entities.movie.searchMovie

import com.example.moviesapi.data.entities.movieLists.PopularMovieResult

data class MovieSearch(
    val page: Int,
    val results: List<PopularMovieResult>,
    val total_pages: Int,
    val total_results: Int
)