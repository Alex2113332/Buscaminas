package com.example.buscaminas.domain

sealed class Difficulty(
    open val rows: Int,
    open val columns: Int,
    val numMines: Int
)

data object EasyDifficulty : Difficulty(9, 9, 10)
data object MediumDifficulty : Difficulty(16, 16, 40)
data object HardDifficulty : Difficulty(16, 32, 99)

data class CustomDifficulty(
    override val rows: Int,
    override val columns: Int,
    val minePercentage: Float
) : Difficulty(
    rows = rows,
    columns = columns,
    numMines = ((rows * columns) * (minePercentage / 100f)).toInt().coerceAtLeast(1)

)

fun Difficulty.toCustomDifficulty() = CustomDifficulty(
    rows = rows,
    columns = columns,
    minePercentage = (numMines.toFloat() / (rows * columns) * 100)
)