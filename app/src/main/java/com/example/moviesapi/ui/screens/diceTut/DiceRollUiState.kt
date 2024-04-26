package com.example.moviesapi.ui.screens.diceTut
sealed interface DiceRollUiState {
    data object Loading : DiceRollUiState
    data class DiceRoll(
        val username: String,
        val numberOfRolls: Int,
        val firstDiceValue: Int? = null,
        val secondDiceValue: Int? = null,
    ) : DiceRollUiState
    data object LogUserIn : DiceRollUiState
}