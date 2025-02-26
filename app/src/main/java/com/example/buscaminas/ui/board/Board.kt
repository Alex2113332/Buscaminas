package com.example.buscaminas.ui.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.ui.cell.Cell
import com.example.buscaminas.ui.cell.State

@Composable
fun Board(width: Int = 6 , height: Int = 6) {
    Column {
        repeat(height) {
            Row {
                repeat(width) {
                    Cell(State.Hidden)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    Board()
}