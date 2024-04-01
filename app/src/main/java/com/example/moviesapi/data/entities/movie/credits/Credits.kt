package com.example.moviesapi.data.entities.movie.credits

data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)