package com.example.moviesapi.data.entities.movie.credits.cast

data class PersonDetailMovieCredits(
    val cast: List<MovieCast>,
    val crew: List<MovieCrew>,
    val id: Int
)