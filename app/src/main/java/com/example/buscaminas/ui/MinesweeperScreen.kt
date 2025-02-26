package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buscaminas.domain.CellState

@Composable
fun MinesweeperScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),

        ) {
        Controls()
        Board(List(6) { List(7) { CellState.Hidden } })
    }
}

@Composable
private fun Controls() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mines: 5"
        )
        IconButton(onClick = {Restart()}) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Happy face",
                tint = Color.Black
            )
        }
        Text(text = "Time: 00:00")
    }
}

fun Restart() {
}

@Preview
@Composable
fun MinesweeperScreenPreview() {
    MinesweeperScreen()
}