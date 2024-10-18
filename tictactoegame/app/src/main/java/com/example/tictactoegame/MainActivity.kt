package com.example.tictactoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.tictactoegame.ui.theme.TicTacToeGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeGameTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(0) }
    var fieldSize by remember { mutableStateOf(0) }
    var lineLength by remember { mutableStateOf(0) }
    var selectedItem by remember { mutableStateOf<GameRecord?>(null) }
    val context = LocalContext.current

    when (currentScreen) {
        0 -> MenuScreen(
            onNavigateToXOConfigPlay = { currentScreen = 1 } ,
            onNavigateToItemList = {
                currentScreen = 3
            }
        )
        1 -> XOConfigPlays(
            onNavigateToXOPlayScreen = { field, line ->
                fieldSize = field
                lineLength = line
                currentScreen = 2 },
            onBackToMenu = { currentScreen = 0 }
        )
        2 -> XOPlayScreen(
            context = context,
            field = fieldSize,
            line = lineLength,
            onBackToXOConfigPlays = { currentScreen = 1 }
        )
        3 -> ItemListScreen(
            context = context,
            onItemSelected = { gameRecord ->
                selectedItem = gameRecord
                currentScreen = 4
            },
            onBackToMenu = { currentScreen = 0 }
        )
        4 -> ItemDetailScreen(
            item = selectedItem,
            onBackToItemList = { currentScreen = 3 }
        )
    }
}

@Composable
fun MenuScreen(
    onNavigateToXOConfigPlay: () -> Unit,
    onNavigateToItemList:() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MENU", fontSize = 30.sp)

        Button(onClick = onNavigateToXOConfigPlay) {
            Text(text = "Go to XO Game")
        }

        Button(onClick = onNavigateToItemList) {
            Text(text = "Go to Item List")
        }
    }
}

