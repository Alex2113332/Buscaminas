package com.example.buscaminas.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.domain.CellState

@Composable
fun Board(cells: List<List<CellState>>,
          onCellClick: (Int, Int) -> Unit = { _, _ -> },
          onCellLongClick: (Int, Int) -> Unit = { _, _ -> }
) {
    Column {
        cells.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex,state ->
                    Cell(
                        state,
                        onClick = {onCellClick(rowIndex, colIndex)},
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
        listOf(CellState.Hidden(hasMine = false), CellState.Visible(1), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Visible(1), CellState.Hidden(hasMine = false)),
        listOf(CellState.Visible(2), CellState.Hidden(hasMine = false), CellState.Visible(2), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false)),
        listOf(CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Visible(2), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false)),
        listOf(CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false)),
        listOf(CellState.Visible(0), CellState.Visible(0), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false)),
        listOf(CellState.Visible(0), CellState.Visible(0), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false), CellState.Hidden(hasMine = false))
    )
    Board(cells)
}
