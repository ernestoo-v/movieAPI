package com.example.moviesapi.data.entities.movieLists

data class PopularMovieList(
    val page: Int,
    val results: List<PopularMovieResult>,
    val total_pages: Int,
    val total_results: Int
)