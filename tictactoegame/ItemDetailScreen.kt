package com.example.tictactoegame
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemDetailScreen(
    item: GameRecord?,
    onBackToItemList: () -> Unit
) {
    var moveIndex by remember { mutableStateOf(
        item?.gameSessions?.firstOrNull()?.moves?.lastIndex ?: 0
    ) }
    Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item?.let { gameRecord ->
            Text(text = "Time: ${gameRecord.time}" )
            gameRecord.gameSessions.forEachIndexed { sessionIndex, session ->
                Text(text = "lineLength: ${session.boardLine?:"-"}" )
                GameBoard(session, moveIndex)
                Row (
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = {
                            if (moveIndex > -1) moveIndex--
                        }
                    ) {
                        Text("<")
                    }
                    Button(onClick = { moveIndex = -1 }) {
                        Text("Reset")
                    }
                    Button(
                        onClick = {
                            if (moveIndex < session.moves.size - 1) moveIndex++
                        }
                    ) {
                        Text(">")
                    }
                }
            }
        }
        Button(onClick = { onBackToItemList() }) {
            Text("Back to Item List")
        }
    }
}

@Composable
fun GameBoard(session: GameSession, moveIndex: Int) {
    val field = session.boardSize
    val board = Array(field) { Array(field) { "" } }

    session.moves.take(moveIndex + 1).forEach { move ->
        board[move.row][move.column] = move.player
    }

    Column {
        for (i in 0 until field) {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (j in 0 until field) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                            .background(Color.White, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = board[i][j],
                            fontSize = 36.sp,
                            color = when (board[i][j]) {
                                "X" -> Color(0xFF4CAF50)
                                "O" -> Color(0xFFFF7043)
                                else -> Color.Black
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
