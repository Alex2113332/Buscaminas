package com.example.buscaminas.domain

abstract class Difficulty(
    val rows: Int,
    val columns: Int,
    val numMines: Int
)

object EasyDifficulty : Difficulty(9, 9, 10)
object MediumDifficulty : Difficulty(16, 16, 40)
object HardDifficulty : Difficulty(16, 32, 99)