package com.example.buscaminas.domain

sealed class Difficulty(
    val rows: Int,
    val columns: Int,
    val numMines: Int
)

data object EasyDifficulty : Difficulty(9, 9, 10)
data object MediumDifficulty : Difficulty(16, 16, 40)
data object HardDifficulty : Difficulty(16, 32, 99)