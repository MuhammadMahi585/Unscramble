package com.example.unscramble.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.INCREASE_SCORE
import com.example.unscramble.data.TOTAL_QUESTIONS
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel(){
    private val _uistate = MutableStateFlow(GameUIState())
    val uiState : StateFlow<GameUIState> = _uistate.asStateFlow()

    private val displayedWord :MutableSet<String> = mutableSetOf("")

    private lateinit var currentWord: String

    var userGuess by mutableStateOf("")
    private fun randomWord(): String{
        currentWord = allWords.random()
        if(displayedWord.contains(currentWord)){
            return randomWord()
        }
        else{
            displayedWord.add(currentWord)
            return shuffleWord(currentWord)
        }
    }

    private fun shuffleWord(word:String):String{
        val wordChar = word.toCharArray()
        while (String(wordChar).equals(word)) {
          wordChar.shuffle()
        }
        return String(wordChar)
        }

    fun checkUserGuess(){
        if(currentWord.equals(userGuess)){
                    val updatedScore = _uistate.value.score.plus(INCREASE_SCORE)
                    updatestate(updatedScore =updatedScore)
            }
        else{
          _uistate.update {
              it.copy(
                  isGuessedWordWrong = true
              )
          }
        }
        userGuess=""
    }
    fun userGuessed(GuessedWord: String){
        userGuess= GuessedWord
    }
    fun updatestate(
      updatedScore:Int
    ){
       if(displayedWord.size==TOTAL_QUESTIONS){
           _uistate.update {currentState->
               currentState.copy(
                  isGuessedWordWrong = false,
                   isGameOver = true,
                   score = updatedScore
               )
           }
       }
        else{
            _uistate.update {
                it.copy(
                    score = updatedScore,
                    currentScrambledWords = randomWord(),
                    isGameOver = false,
                    CurrentWordCount = it.CurrentWordCount.inc()
                )
            }
       }
    }
    fun skipWord(){
      updatestate(_uistate.value.score)
    }
    fun getCurrentWord():String{
         return currentWord
    }
    fun resetgame(){
        displayedWord.clear()
        _uistate.value = GameUIState(currentScrambledWords = randomWord())
    }
    init{
        resetgame()
    }
    }

