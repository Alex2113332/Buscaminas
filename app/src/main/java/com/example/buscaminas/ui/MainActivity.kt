package com.example.buscaminas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.buscaminas.domain.CellState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperScreen(3, 6, cells = listOf(
                listOf(CellState.Hidden, CellState.Visible(1), CellState.Hidden, CellState.Hidden, CellState.Visible(1), CellState.Hidden),
                listOf(CellState.Visible(2), CellState.Hidden, CellState.Visible(2), CellState.Hidden, CellState.Hidden, CellState.Hidden),
                listOf(CellState.Hidden, CellState.Hidden, CellState.Visible(2), CellState.Hidden, CellState.Hidden, CellState.Hidden),
                listOf(CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden, CellState.Hidden)),
            )
        }
    }
}

