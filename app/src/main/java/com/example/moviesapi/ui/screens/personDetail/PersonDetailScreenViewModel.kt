package com.example.moviesapi.ui.screens.personDetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.data.entities.movie.BelongsToCollection
import com.example.moviesapi.data.entities.movie.Movie
import com.example.moviesapi.data.entities.movie.credits.Credits
import com.example.moviesapi.data.entities.movie.credits.cast.MovieCast
import com.example.moviesapi.data.entities.movie.credits.cast.MovieCrew
import com.example.moviesapi.data.entities.movie.credits.cast.PersonDetail
import com.example.moviesapi.data.entities.movie.credits.cast.PersonDetailMovieCredits
import com.example.moviesapi.data.entities.movie.credits.cast.image.Profile
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult
import com.example.moviesapi.data.network.di.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PersonDetailScreenViewModel : ViewModel() {
    private val apiService = ApiService.RetrofitInstance.api
    val loading: MutableState<Boolean> = mutableStateOf(true)
    val personDetail: MutableState<PersonDetail> = mutableStateOf(
        PersonDetail(
            adult = false,
            also_known_as = emptyList(),
            biography = "",
            birthday = "",
            deathday = "",
            gender = 0,
            homepage = "",
            id = 4,
            imdb_id = "",
            known_for_department = "",
            name = "",
            place_of_birth = "",
            popularity = 0.0,
            profile_path = ""
        )
    )
    val personDetailMovieCredits: MutableState<List<MovieCast>> =
        mutableStateOf(emptyList())

    val personImages: MutableState<List<Profile>> =
        mutableStateOf(emptyList())

    fun getPersonDetail(personId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonDetail(personId)
                personDetail.value = response
            } catch (e: Exception) {
                Log.e("MainScreenViewModel", "Error apiService.PersonDetail -> $e")
            } finally {
                delay(1000)
                loading.value = false
            }
        }
    }

    fun getPersonImages(personId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonImages(personId).profiles

                val sortedImages = response.sortedByDescending { it.vote_average }

                personImages.value = response
            } catch (e: Exception) {
                Log.e("MainScreenViewModel", "Error apiService.PersonDetail -> $e")
            } finally {
                delay(1000)
                loading.value = false
            }
        }
    }

    fun getPersonDetailMovieCredits(personId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonDetailMovieCredits(personId).cast
                val filteredCast = response.filter { !it.adult }
                val sortedCast = filteredCast.sortedByDescending { it.popularity }
                personDetailMovieCredits.value = sortedCast
            } catch (e: Exception) {
                Log.e("MainScreenViewModel", "Error apiService.PersonDetailMoviesCredits -> $e")
            }
        }
    }
}