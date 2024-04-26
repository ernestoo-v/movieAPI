package com.example.moviesapi.ui.screens.movie.movieDiscover.search2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    authorsRepository: AuthorsRepository,
    topicsRepository: TopicsRepository
) : ViewModel() {

    val uiState: StateFlow<MovieDiscoverUiState> = ...

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.toggleFollowedTopicId(followedTopicId, followed)
        }
    }
