package com.example.buscaminas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperScreen()
        }
    }
}

@Composable
fun Cell() {
    Box(
    ) {
        Text(
            text = "1",
            color = Color.White
        )
    }
}

@Composable
fun MinesweeperScreen() {
    Cell()
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MinesweeperScreen()
}
