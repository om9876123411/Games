package com.example.tictactoegame

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileInputStream

@Composable
fun ItemListScreen(
    context: Context,
    onItemSelected: (GameRecord) -> Unit,
    onBackToMenu: () -> Unit
) {
    val items = remember { mutableStateOf(listOf<GameRecord>()) }
    LaunchedEffect(Unit) {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            val inputStream = FileInputStream(file)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val jsonText = bufferedReader.use { it.readText() }
            val itemType = object : TypeToken<DateRecord>() {}.type
            val dateRecord: DateRecord = Gson().fromJson(jsonText, itemType)

            items.value = dateRecord.date.sortedByDescending { it.time }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.05f))
        LazyColumn (
            modifier = Modifier.weight(1f)
        ){
            items(items.value) { gameRecord ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemSelected(gameRecord) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Session time: ${gameRecord.time}",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
        Button(onClick = { onBackToMenu() }) {
            Text("Back to Menu")
        }
    }
}