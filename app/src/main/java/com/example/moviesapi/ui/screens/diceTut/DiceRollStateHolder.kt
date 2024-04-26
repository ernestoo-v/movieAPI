package com.example.moviesapi.ui.screens.diceTut

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DiceRollStateHolder(
) {

    private val _uiState = MutableStateFlow(DiceRollUiState())
    val uiState: StateFlow<DiceRollUiState> = _uiState.asStateFlow()

    fun rollDice() {
        _uiState.update { currentState ->
            currentState.copy(
                firstDiceValue = (0..6).random(),
                secondDiceValue = (0..6).random(),
                numberOfRolls = currentState.numberOfRolls + 1
            )
        }
    }
}
