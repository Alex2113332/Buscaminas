package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    userLooses: Boolean = false,
    userWins: Boolean = false,
    cells: List<List<CellState>> = List(6) { List(7) { CellState.Hidden(hasMine = false) } },
    onClick: () -> Unit = {},
    onCellClick: (Int, Int) -> Unit = { _, _ -> },
    onCellLongClick: (Int, Int) -> Unit = { _, _ -> },
    onDifficultySelected: (String) -> Unit = {}
) {
    var selectedDifficulty = rememberSaveable { mutableStateOf("Principiante") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {

        Row (
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(onClick = {
                selectedDifficulty.value = "Principiante"
                onDifficultySelected("Principiante")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDifficulty.value == "Principiante") Color.Gray else Color.Unspecified,
                )
            ) {
                Text("Principiante")
            }
            Button(onClick = {
                selectedDifficulty.value = "Intermedio"
                onDifficultySelected("Intermedio")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDifficulty.value == "Intermedio") Color.Gray else Color.Unspecified,
                )
            ) {
                Text("Intermedio")
            }
            Button(onClick = {
                selectedDifficulty.value = "Avanzado"
                onDifficultySelected("Avanzado")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDifficulty.value == "Avanzado") Color.Gray else Color.Unspecified,
                )
            ) {
                Text("Avanzado")
            }
        }

        Controls(
            minesRemaining,
            time = time,
            onClick = onClick,
            userLooses = userLooses,
            userWins = userWins
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
                .verticalScroll(rememberScrollState())
        ) {
            Board(
                cells,
                onCellClick = { rowIndex, colIndex ->
                    if (!userLooses && !userWins) {
                        onCellClick(rowIndex, colIndex)
                    }
                },
                onCellLongClick = { rowIndex, colIndex ->
                    if (!userLooses && !userWins) {
                        onCellLongClick(rowIndex, colIndex)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun MinesweeperScreenPreview() {
    MinesweeperScreen(onClick = { })
}