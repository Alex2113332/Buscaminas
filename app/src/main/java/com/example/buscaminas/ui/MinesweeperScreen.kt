package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buscaminas.domain.*

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
    onDifficultySelected: (Difficulty) -> Unit = {},
    selectedDifficulty: Difficulty
) {
    var isDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        DifficultySelector(onDifficultySelected, selectedDifficulty) {
            isDialogVisible = true
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
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopCenter
        ) {
            Board(
                cells,
                onCellClick = { rowIndex, colIndex ->
                    onCellClick(rowIndex, colIndex)
                },
                onCellLongClick = { rowIndex, colIndex ->
                    onCellLongClick(rowIndex, colIndex)
                }
            )
        }
    }

    if (isDialogVisible) {
        DifficultySelectionDialog(
            selectedDifficulty = selectedDifficulty.toCustomDifficulty(),
            onDifficultySelected = { difficulty ->
                if (difficulty != null) {
                    onDifficultySelected(difficulty)
                }
                isDialogVisible = false
            },
        )
    }
}

@Preview
@Composable
fun MinesweeperScreenPreview() {

    MinesweeperScreen(
        onClick = {},
        selectedDifficulty = EasyDifficulty,
    )
}
