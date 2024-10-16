package com.example.tictactoegame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun XOConfigPlays(
    onNavigateToXOPlayScreen: (Int, Int) -> Unit,
    onBackToMenu: () -> Unit
) {
    var field by remember { mutableStateOf("") }
    var line by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Setting Play", fontSize = 30.sp)

        TextField(
            value = field,
            onValueChange = { field = it },
            label = { Text("Enter number fieldSize") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = line,
            onValueChange = { line = it },
            label = { Text("Enter number lineLength") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = {
                val field = field.toIntOrNull() ?: 3
                val line = line.toIntOrNull() ?: 3
                onNavigateToXOPlayScreen(field, line)
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Play")
        }
        Button(
            onClick = onBackToMenu
        ) {
            Text("Back to MENU")
        }
    }
}