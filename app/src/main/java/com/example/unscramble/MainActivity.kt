package com.example.unscramble


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import com.example.unscramble.ui.theme.GameScreen

import com.example.unscramble.ui.theme.UnscrambleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnscrambleTheme {
                Greeting()
            GameScreen(context = LocalContext.current)
            }
        }
    }
}
@Composable
fun Greeting(
){
    var showDialog by remember {
        mutableStateOf(true)
    }
    if(showDialog){
    AlertDialog(onDismissRequest = { /*TODO*/ },
        title = {
            Text(text = "Well come")
        },
        text = {
            Image(painter = painterResource(id = R.drawable.wellcome),
                contentDescription = "wellcoming image")
        },
      confirmButton = {
          TextButton(onClick = {showDialog=false}) {
              Text(text = "Go to App")
          }
       })
    }
}



