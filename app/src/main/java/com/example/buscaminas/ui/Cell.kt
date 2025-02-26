package com.example.buscaminas.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

sealed interface State {
    data object Hidden : State
    data class Visible (val minesAround: Int): State
    data object Flagged : State
    data object Mine : State
    data object MineExploded : State
}

@Composable
fun Cell(cellState: State) {
    val cellText = cellState.content()
    Box(
        modifier = Modifier
            .border(1.dp, Color.Red)
            .aspectRatio(1f)
    ) {
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