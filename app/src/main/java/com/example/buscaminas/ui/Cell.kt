package com.example.buscaminas.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.buscaminas.domain.CellState

@Composable
fun Cell(cellState: CellState) {
    val cellText = cellState.content()
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(1.dp, Color.Red)
            .background(Color.White)
            .aspectRatio(1f)
            .clickable {
                Log.d("xxy", "click")
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = cellText,
            color = Color.Black
        )
    }
}

@Composable
private fun CellState.content(): String {
    return when (this) {
        CellState.Hidden -> "H"
        is CellState.Visible -> this.minesAround.toString()
        CellState.Flagged -> "F"
        CellState.Mine -> "M"
        CellState.MineExploded -> "X"
    }
}

@Preview
@Composable
fun CellHiddenPreview() {
    Cell(cellState = CellState.Hidden)
}

@Preview
@Composable
fun CellVisiblePreview() {
    Cell(cellState = CellState.Visible(3))
}

@Preview
@Composable
fun CellFlaggedPreview() {
    Cell(cellState = CellState.Flagged)
}

@Preview
@Composable
fun CellMinePreview() {
    Cell(cellState = CellState.Mine)
}

@Preview
@Composable
fun CellMineExplodedPreview() {
    Cell(cellState = CellState.MineExploded)
}