package com.example.buscaminas.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

enum class CellState {
    HIDDEN,
    VISIBLE,
    FLAGGED,
    MINE,
    MINE_EXPLODED
}

@Composable
fun Cell(cellState: CellState) {
    val cellText = cellState.content()
    Box {
        Text(
            text = cellText,
            color = Color.White
        )
    }
}

@Composable
private fun CellState.content(): String {
    val cellText = when (this) {
        CellState.HIDDEN -> "H"
        CellState.VISIBLE -> "V"
        CellState.FLAGGED -> "F"
        CellState.MINE -> "M"
        CellState.MINE_EXPLODED -> "X"
    }
    return cellText
}

@Preview
@Composable
fun CellHiddenPreview() {
    Cell(cellState = CellState.HIDDEN)
}
@Preview
@Composable
fun CellVisiblePreview() {
    Cell(cellState = CellState.VISIBLE)
}
@Preview
@Composable
fun CellFlaggedPreview() {
    Cell(cellState = CellState.FLAGGED)
}