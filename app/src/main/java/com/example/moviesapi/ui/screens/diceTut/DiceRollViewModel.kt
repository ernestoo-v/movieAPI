package com.example.moviesapi.ui.screens.diceTut

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
class DiceRollViewModel(
    userRepository: UserRepository
) : ViewModel() {

    val nombreUser:String="Carlos"

    val userUiState: StateFlow<String> =
        userRepository.userStream.map { user -> user.name }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ""
            )
}