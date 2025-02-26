package com.example.buscaminas.ui.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.ui.board.Board

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperScreen()
        }
    }
}

@Composable
fun MinesweeperScreen() {
    Board()
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MinesweeperScreen()
}
