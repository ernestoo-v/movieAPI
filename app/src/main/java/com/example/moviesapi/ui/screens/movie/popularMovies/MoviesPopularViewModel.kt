package com.example.moviesapi.ui.screens.movie.popularMovies

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult
import com.example.moviesapi.data.network.di.ApiService
import kotlinx.coroutines.launch

class MoviesPopularViewModel : ViewModel() {

    private val apiService = ApiService.RetrofitInstance.api
    val popularMovies: MutableState<List<PopularMovieResult>> = mutableStateOf(emptyList())

    val loading: MutableState<Boolean> = mutableStateOf(true)
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
}