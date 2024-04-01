package com.example.moviesapi.ui.screens.movie.movieDetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.data.entities.movie.BelongsToCollection
import com.example.moviesapi.data.entities.movie.Movie
import com.example.moviesapi.data.entities.movie.credits.Credits
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult
import com.example.moviesapi.data.network.di.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val apiService = ApiService.RetrofitInstance.api
    val loading: MutableState<Boolean> = mutableStateOf(true)

    val movieCredits: MutableState<Credits> = mutableStateOf(
        Credits(
            cast = emptyList(),
            crew = emptyList(),
            id = 0
        )
    )

    val movieDetail: MutableState<Movie> = mutableStateOf(
        Movie(
            adult = false,
            backdrop_path = "",
            belongs_to_collection = BelongsToCollection("", 0, "", ""),
            budget = 0,
            genres = emptyList(),
            homepage = "",
            id = 0,
            imdb_id = "",
            original_language = "",
            original_title = "",
            overview = "",
            popularity = 0.0,
            poster_path = "",
            production_companies = emptyList(),
            production_countries = emptyList(),
            release_date = "",
            revenue = 0,
            runtime = 0,
            spoken_languages = emptyList(),
            status = "",
            tagline = "",
            title = "",
            video = false,
            vote_average = 0.0,
            vote_count = 0
        )
    )

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getMovieDetail(movieId)
                Log.d("Revisar", " apiService.MovieDetail -> $response")

                movieDetail.value = response
            } catch (e: Exception) {

                Log.e("MainScreenViewModel", "Error apiService.MovieDetailss -> $e " )
            }
            finally {
                delay(1000)
                loading.value=false
            }
        }
    }

    fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getMovieCredits(movieId)
                movieCredits.value = response
            } catch (e: Exception) {
                Log.e("MainScreenViewModel", "Error apiService.MovieDetail -> $e")
            }
        }
    }
}