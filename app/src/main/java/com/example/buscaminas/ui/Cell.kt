package com.example.buscaminas.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

sealed interface State {
    data object Hidden : State
    class Visible (val minesAround: Int): State
    data object Flagged : State
    data object Mine : State
    data object MineExploded : State
}

@Composable
fun Cell(cellState: State) {
    val cellText = cellState.content()
    Box {
        Text(
            text = cellText,
            color = Color.White
        )
    }
}

@Composable
private fun State.content(): String {
    return when (this) {
        State.Hidden -> "H"
        is State.Visible -> this.minesAround.toString()
        State.Flagged -> "F"
        State.Mine -> "M"
        State.MineExploded -> "X"
    }
}

@Preview
@Composable
fun CellHiddenPreview() {
    Cell(cellState = State.Hidden)
}

@Preview
@Composable
fun CellVisiblePreview() {
    Cell(cellState = State.Visible(3))
}

@Preview
@Composable
fun CellFlaggedPreview() {
    Cell(cellState = State.Flagged)
}

@Preview
@Composable
fun CellMinePreview() {
    Cell(cellState = State.Mine)
}

@Preview
@Composable
fun CellMineExplodedPreview() {
    Cell(cellState = State.MineExploded)
}