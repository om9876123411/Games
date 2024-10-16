package com.example.tictactoegame

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun XOPlayScreen(
    context: Context,
    onBackToXOConfigPlays : () -> Unit,
    field : Int,
    line : Int
) {
    var board by remember { mutableStateOf(List(field) { MutableList(field) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }
    var boardfull by remember { mutableStateOf(false) }
    val moveHistory = remember { mutableStateListOf<MoveLog>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tic-Tac-Toe",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        for (i in 0 until field) {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (j in 0 until field) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .padding(4.dp)
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .clickable(enabled = board[i][j].isEmpty() && winner == null
                            ) {
                                board = board.toMutableList().apply {
                                    this[i][j] = currentPlayer
                                }
                                moveHistory.add(MoveLog(player = currentPlayer, row = i, column = j))
                                winner = checkWinner(field,line,board)
                                boardfull = isBoardFull(board)
                                if (winner == null && !boardfull) {
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                }else {
                                    val gameSession = GameSession(boardSize = field, boardLine = line , moves = moveHistory)
                                    val currentDateTime = LocalDateTime.now()
                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss")
                                    val formattedTime = currentDateTime.format(formatter)
                                    val gameRecord = GameRecord(time = formattedTime, gameSessions = listOf(gameSession))
                                    val gson = Gson()
                                    val oldJson = readJsonFromFile(context, fileName)
                                    val dateRecords: DateRecord

                                    if (oldJson != null) {
                                        dateRecords = gson.fromJson(oldJson, DateRecord::class.java)
                                        dateRecords.date.add(gameRecord)
                                    } else {
                                        dateRecords = DateRecord(date = mutableListOf(gameRecord))
                                    }
                                    val jsonLog = gson.toJson(dateRecords)
                                    saveJsonToFile(context, jsonLog, fileName)
                                }
                            },
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
        Spacer(modifier = Modifier.height(16.dp))

        if (winner != null) {
            Text(text = "Winner: $winner",
                fontSize = 28.sp
            )
        } else if (boardfull) {
            Text(
                text = "It's a draw!",
                fontSize = 28.sp
            )
        } else {
            Text(text = "Current Player: $currentPlayer", fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                board = List(field) { MutableList(field) { "" } }
                currentPlayer = "X"
                winner = null
            },
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
        ) {
            Text(text = "Play Again")
        }
        Button(
            onClick = onBackToXOConfigPlays ,
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF48FB1))
        ) {
            Text(text = "Exit")
        }
    }
}

// ฟังก์ชันตรวจสอบผู้ชนะ
fun checkWinner(field: Int, line: Int, board: List<MutableList<String>>): String? {
    fun checkRows(): String? {
        for (row in board) {
            for (i in 0..(field - line)) {
                if (row[i] != "" && (1 until line).all { j -> row[i] == row[i + j] }) {
                    return row[i]
                }
            }
        }
        return null
    }

    fun checkColumns(): String? {
        for (col in 0 until field) {
            for (i in 0..(field - line)) {
                if (board[i][col] != "" && (1 until line).all { j -> board[i][col] == board[i + j][col] }) {
                    return board[i][col]
                }
            }
        }
        return null
    }

    fun checkDiagonals(): String? {
        for (i in 0..(field - line)) {
            for (j in 0..(field - line)) {
                if (board[i][j] != "" && (1 until line).all { k -> board[i][j] == board[i + k][j + k] }) {
                    return board[i][j]
                }
                if (board[i][j + line - 1] != "" && (1 until line).all { k -> board[i][j + line - 1] == board[i + k][j + line - 1 - k] }) {
                    return board[i][j + line - 1]
                }
            }
        }
        return null
    }
    return checkRows() ?: checkColumns() ?: checkDiagonals()
}

// ฟังก์ชันตรวจสอบว่ากระดานเต็มหรือยัง
fun isBoardFull(board: List<MutableList<String>>): Boolean {
    for (row in board) {
        for (cell in row) {
            if (cell.isEmpty()) {
                return false
            }
        }
    }
    return true
}
