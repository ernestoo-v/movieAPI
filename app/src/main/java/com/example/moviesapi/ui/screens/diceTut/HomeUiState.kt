package com.example.moviesapi.ui.screens.diceTut

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String

    data class NoPosts(
        override val isLoading: Boolean = false,
        override val errorMessages: List<ErrorMessage> = emptyList(),
        override val searchInput: String = ""
    ) : HomeUiState

    data class HasPosts(
        val postsFeed: PostsFeed,
        val selectedPost: Post,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean = false,
        override val errorMessages: List<ErrorMessage> = emptyList(),
        override val searchInput: String = ""
    ) : HomeUiState

    fun toUiState(): HomeUiState =
        if (postsFeed == null) {
            HomeUiState.NoPosts(...)
        } else {
            HomeUiState.HasPosts(...)
        }
}

