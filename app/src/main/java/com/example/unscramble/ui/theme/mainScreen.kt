package com.example.unscramble.ui.theme

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(),
    context: Context
){
    val gameUIState by gameViewModel.uiState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .statusBarsPadding()
            .safeDrawingPadding()
            .padding(
                horizontal = dimensionResource(id = R.dimen.mediumpadding)
            )) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.largepadding)))
        GameLayout(
            userGuess = gameViewModel.userGuess,
            onGuessedChange = {gameViewModel.userGuessed(it)},
            isGuessedWrong = gameUIState.isGuessedWordWrong)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.mediumpadding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.mediumpadding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextButton(onClick = {
                val word= gameViewModel.getCurrentWord()
                Toast.makeText(context,"first letter is ${word.first()} \nlast letter is ${word[word.length-1]}",Toast.LENGTH_LONG).show()
            }) {
                Text(text = "Hint")
            }
        Button(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth(),
            onClick = {gameViewModel.checkUserGuess() }) {
            Text(text = stringResource(R.string.submit))
        }
            val current=  gameViewModel.getCurrentWord()
            OutlinedButton(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                ,onClick = {gameViewModel.skipWord()
                Toast.makeText(context,"correct word is "+current,Toast.LENGTH_LONG).show()}) {
                Text(text = "Skip")
            }
            GameStatus(score = gameUIState.score)
    }
    }
    if(gameUIState.isGameOver){
      ScoreDialog(score = gameUIState.score,
         onPlayAgain = {gameViewModel.resetgame()} )
    }
}
@Composable
fun GameLayout(
    gameViewModel2: GameViewModel = viewModel(),
    userGuess: String,
    onGuessedChange :(String) -> Unit,
    isGuessedWrong : Boolean
){
    val gameUIState by gameViewModel2.uiState.collectAsState()
    Card(
        elevation = CardDefaults.cardElevation(5.dp)
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.mediumpadding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.mediumpadding))
        ) {
            Text(
                text = "${gameUIState.CurrentWordCount}/10",
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(MaterialTheme.shapes.small)
                    .background(color = MaterialTheme.colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        Text(
            text = gameUIState.currentScrambledWords,
            style = MaterialTheme.typography.displayLarge)
        Text(
            text = stringResource(R.string.describe),
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            value = userGuess,
            onValueChange = onGuessedChange,
            label ={ if(isGuessedWrong) (
            Text(text = "Wrong Answer")
        )
        else{
            Text(text= "Enter Word")
        }})

    }
        }
}
@Composable
fun GameStatus(
    score: Int,
){
    Card {
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
fun ScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit
){
    val activity = (LocalContext.current as Activity)
    AlertDialog(onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Congratulation")},
        text = {
            Text(text = "you scored $score")
        },
         dismissButton = {
             TextButton(
                 onClick = {
                     activity.finish()
                 }) {
                 Text(text = "Exit")
             }
         },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                 Text(text = "Play Again")
            }
        })
}
@Preview
@Composable
fun GameDisplay(){
    UnscrambleTheme {
    }
}
@Preview
@Composable
fun GameDisplay2(){
    UnscrambleTheme(darkTheme = true){

    }
}