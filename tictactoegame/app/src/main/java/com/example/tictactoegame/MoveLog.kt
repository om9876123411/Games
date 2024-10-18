package com.example.tictactoegame

import android.content.Context
import java.io.File

data class DateRecord(var date: MutableList<GameRecord>)
data class GameRecord(val time: String, val gameSessions: List<GameSession>)
data class GameSession(val boardSize: Int, val boardLine : Int, val moves: List<MoveLog>)
data class MoveLog(val player: String, val row: Int, val column: Int)

val fileName = "moveHistory.json"

fun saveJsonToFile(context: Context, jsonLog: String, fileName: String) {
    val file = File(context.filesDir, fileName)
    file.writeText(jsonLog)
}

fun readJsonFromFile(context: Context, fileName: String): String? {
    return try {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            file.readText()
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}