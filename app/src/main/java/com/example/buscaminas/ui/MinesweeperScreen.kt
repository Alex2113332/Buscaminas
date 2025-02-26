package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buscaminas.domain.CellState

@Composable
fun MinesweeperScreen() {
    val minesRemaining = 5
    val time = 0
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),

        ) {
        Controls(minesRemaining, time)
        Board(List(6) { List(7) { CellState.Hidden } })
    }
}

fun Restart() {
}

@Preview
@Composable
fun MinesweeperScreenPreview() {
    MinesweeperScreen()
}