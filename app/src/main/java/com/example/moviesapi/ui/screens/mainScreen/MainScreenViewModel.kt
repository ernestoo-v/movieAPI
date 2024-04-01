package com.example.moviesapi.ui.screens.mainScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.data.entities.movie.searchMovie.MovieSearchResult
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult
import com.example.moviesapi.data.network.di.ApiService
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    private val apiService = ApiService.RetrofitInstance.api
    val popularMovies: MutableState<List<PopularMovieResult>> = mutableStateOf(emptyList())
    val moviesSearch: MutableState<List<PopularMovieResult>> = mutableStateOf(emptyList())

    val loading: MutableState<Boolean> = mutableStateOf(true)
    val loadingSearch: MutableState<Boolean> = mutableStateOf(true)


    fun getPopularMovies(pageNumber: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPopularMovies("en-US", pageNumber).results
                Log.d("Revisar", "API RESULT -> $response")
                if (response.isNotEmpty()) {
                    popularMovies.value = response
                }
            } catch (e: Exception) {
                Log.e("Revisar", "Error apiService.getPopularMovies -> $e")
            } finally {
                loading.value = false
            }
        }
    }

    fun getSearchMovie(query: String, include_adult: Boolean, language: String, pageNumber: Int) {
        viewModelScope.launch {
            try {
                val response =
                    apiService.getMovieSearch(query, include_adult, language, pageNumber).results
                Log.d("Revisar", "API RESULT Search Movies -> $response")

                val sortedSearchMovies = response.sortedByDescending { it.popularity }

                if (sortedSearchMovies.isNotEmpty()) {
                    moviesSearch.value = sortedSearchMovies
                }
            } catch (e: Exception) {
                Log.e("Revisar", "Error apiService.getPopularMovies -> $e")
            } finally {
                loadingSearch.value = false
            }
        }
    }
}