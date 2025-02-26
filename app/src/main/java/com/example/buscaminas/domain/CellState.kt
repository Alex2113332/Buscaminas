package com.example.buscaminas.domain

sealed interface CellState {
    data object Hidden : CellState
    data class Visible (val minesAround: Int): CellState
    data object Flagged : CellState
    data object Mine : CellState
    data object MineExploded : CellState
}