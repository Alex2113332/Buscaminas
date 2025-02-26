package com.example.buscaminas.ui

import android.util.Log
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
fun MinesweeperScreen(
    minesRemaining: Int = 5,
    time: Int = 0,
    cells: List<List<CellState>> = List(6) { List(7) { CellState.Hidden } },
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),

        ) {
        Controls(minesRemaining, time, onClick)
        Board(cells)
    }
}

@Preview
@Composable
fun MinesweeperScreenPreview() {
    MinesweeperScreen(onClick = { Log.d("xxx", "click") })
}