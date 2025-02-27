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
fun Cell(cellState: CellState, onClick: () -> Unit = {}) {
    val cellText = cellState.content()
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(1.dp, Color.Red)
            .background(Color.White)
            .aspectRatio(1f)
            .clickable(onClick = onClick),
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
        is CellState.Hidden -> if (this.hasMine) "M" else "H"
        is CellState.Visible -> this.minesAround.toString()
        is CellState.Flagged -> "F"
        CellState.MineExploded -> "X"
    }
}


@Preview
@Composable
fun CellHiddenPreview() {
    Cell(cellState = CellState.Hidden(hasMine = false), onClick = {
        Log.d("xxy", "click")
    })
}

@Preview
@Composable
fun CellVisiblePreview() {
    Cell(cellState = CellState.Visible(3), onClick = {
        Log.d("xxy", "click")
    })
}

@Preview
@Composable
fun CellFlaggedPreview() {
    Cell(cellState = CellState.Flagged(hasMine = false), onClick = {
        Log.d("xxy", "click")
    })
}

@Preview
@Composable
fun CellMineExplodedPreview() {
    Cell(cellState = CellState.MineExploded, onClick = {
        Log.d("xxy", "click")
    })
}