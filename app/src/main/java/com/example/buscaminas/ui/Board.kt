package com.example.buscaminas.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.domain.CellState

@Composable
fun Board(
    cells: List<List<CellState>>,
    onCellClick: (Int, Int) -> Unit = { _, _ -> },
    onCellLongClick: (Int, Int) -> Unit = { _, _ -> }
) {
    Column {
        cells.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, state ->
                    Cell(
                        cellState = state,
                        onClick = { onCellClick(rowIndex, colIndex) },
                        onLongClick = { onCellLongClick(rowIndex, colIndex) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    val cells = listOf(
        listOf(
        CellState.Hidden(hasMine = false),
        CellState.Visible(minesAround = 3),
        CellState.Flagged(hasMine = false),
        CellState.Mine,
        CellState.MineExploded
        ),
        listOf(
            CellState.Hidden(hasMine = true),
            CellState.Visible(minesAround = 0),
            CellState.Flagged(hasMine = true),
            CellState.Mine,
            CellState.MineExploded
        )
    )
    Board(cells)
}
