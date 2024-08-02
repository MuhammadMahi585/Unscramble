package com.example.unscramble.ui.theme



data class GameUIState(
    val score: Int = 0,
    val currentScrambledWords: String = "",
    val CurrentWordCount: Int = 1,
    val isGameOver: Boolean = false,
    val isGuessedWordWrong: Boolean = false
)
