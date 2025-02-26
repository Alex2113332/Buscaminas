package com.example.buscaminas.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.domain.CellState

@Composable
fun Board(cells: List<List<CellState>>) {
    Column {
        cells.forEach { row ->
            Row {
                row.forEach { state ->
                    Cell(state) {
                        Log.d("xxy", "click")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    val cells = listOf(
        listOf(CellState.Hidden, CellState.Visible(1), CellState.Hidden, CellState.Hidden, CellState.Visible(1), CellState.Hidden),
        listOf(CellState.Visible(2), CellState.Hidden, CellState.Visible(2), CellState.Hidden, CellState.Hidden, CellState.Hidden),
        listOf(CellState.Hidden, CellState.Hidden, CellState.Visible(2), CellState.Hidden, CellState.Hidden, CellState.Hidden),
        listOf(CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden),
        listOf(CellState.Visible(0), CellState.Visible(0), CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden),
        listOf(CellState.Visible(0), CellState.Visible(0), CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden)
    )
    Board(cells)
}