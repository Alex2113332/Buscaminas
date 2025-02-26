package com.example.buscaminas.ui.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.ui.cell.Cell
import com.example.buscaminas.ui.cell.State

@Composable
fun Board(cells: List<List<State>>) {
    Column {
        cells.forEach { row ->
            Row {
                row.forEach { state ->
                    Cell(state)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    val cells = listOf(
        listOf(State.Hidden, State.Visible(1), State.Hidden,State.Hidden, State.Visible(1), State.Hidden),
        listOf(State.Visible(2), State.Hidden, State.Visible(2), State.Hidden, State.Hidden, State.Hidden),
        listOf(State.Hidden, State.Hidden, State.Visible(2), State.Hidden, State.Hidden, State.Hidden),
        listOf(State.Hidden, State.Hidden, State.Hidden, State.Hidden, State.Hidden, State.Hidden),
        listOf(State.Visible(0), State.Visible(0), State.Hidden, State.Hidden, State.Hidden, State.Hidden),
        listOf(State.Visible(0), State.Visible(0), State.Hidden, State.Hidden, State.Hidden, State.Hidden)
    )
    Board(cells)
}