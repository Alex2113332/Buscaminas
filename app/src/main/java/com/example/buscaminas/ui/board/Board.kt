package com.example.buscaminas.ui.board

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.ui.cell.Cell
import com.example.buscaminas.ui.cell.State

@Composable
fun Board() {
    Cell(State.Hidden)
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    Board()
}