package com.example.buscaminas.domain

sealed interface CellState {
    data class Hidden (val hasMine: Boolean): CellState
    data class Visible (val minesAround: Int): CellState
    data class Flagged (val hasMine: Boolean): CellState
    data object Mine : CellState
    data object MineExploded : CellState
}