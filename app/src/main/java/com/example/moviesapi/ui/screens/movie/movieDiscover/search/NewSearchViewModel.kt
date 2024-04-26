package com.example.moviesapi.ui.screens.movie.movieDiscover.search

import android.app.appsearch.SearchResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.data.entities.movieLists.PopularMovieResult
import com.example.moviesapi.data.network.di.ApiService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class NewSearchViewModel : ViewModel() {
    private val apiService = ApiService.RetrofitInstance.api

    sealed interface ViewState {
        object IdleScreen : ViewState
        object Loading : ViewState
        object Error : ViewState
        object NoResults : ViewState
        data class SearchResultsFetched(val results: List<PopularMovieResult>) : ViewState
    }

    sealed interface SearchFieldState {
        object Idle : SearchFieldState
        object EmptyActive : SearchFieldState
        object WithInputActive : SearchFieldState
    }

    private val _searchFieldState: MutableStateFlow<SearchFieldState> =
        MutableStateFlow(SearchFieldState.Idle)
    val searchFieldState: StateFlow<SearchFieldState> = _searchFieldState

    private val _viewState: MutableStateFlow<ViewState> =
        MutableStateFlow(ViewState.IdleScreen)
    val viewState: StateFlow<ViewState> = _viewState

    private val _inputText: MutableStateFlow<String> =
        MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val isInitialized = AtomicBoolean(false)

    @FlowPreview
    fun initialize() {
        if (isInitialized.compareAndSet(false, true)) {
            viewModelScope.launch {
                inputText.debounce(500).collectLatest { input ->
                    if (input.blankOrEmpty()) {
                        _viewState.update { ViewState.IdleScreen }
                        return@collectLatest
                    }
                    val result = apiService.getMovieSearch(input, false, "en-US", 1)
                    if (result.results.isEmpty()) {
                        _viewState.update { ViewState.NoResults }
                    } else {
                        _viewState.update { ViewState.SearchResultsFetched(result.results) }
                    }
                }
            }
        }
    }

    fun updateInput(inputText: String) {
        _inputText.update { inputText }
        activateSearchField()

        if (inputText.blankOrEmpty().not()) {
            _viewState.update { ViewState.Loading }
        }
    }

    fun searchFieldActivated() {
        activateSearchField()
    }

    fun clearInput() {
        _viewState.update { ViewState.Loading }
        _inputText.update { "" }
        _searchFieldState.update { SearchFieldState.EmptyActive }
    }

    fun revertToInitialState() {
        _viewState.update { ViewState.IdleScreen }
        _inputText.update { "" }
        _searchFieldState.update { SearchFieldState.Idle }
    }

    private fun activateSearchField() {
        if (inputText.value.blankOrEmpty().not()) {
            _searchFieldState.update { SearchFieldState.WithInputActive }
        } else {
            _searchFieldState.update { SearchFieldState.EmptyActive }
        }
    }

    private fun String.blankOrEmpty() = this.isBlank() || this.isEmpty()
}