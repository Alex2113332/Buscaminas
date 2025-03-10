package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.buscaminas.R
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
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                listOf(
                    EasyDifficulty to R.string.Easy,
                    MediumDifficulty to R.string.Medium,
                    HardDifficulty to R.string.Hard
                )
            ) { (difficulty, stringRes) ->
                Button(
                    onClick = { onDifficultySelected(difficulty) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedDifficulty == difficulty) Color.Black else Color.Gray,
                    )
                ) {
                    Text(stringResource(stringRes))
                }
            }

            item {
                Button(
                    onClick = { isDialogVisible = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedDifficulty is CustomDifficulty) Color.Black else Color.Gray,
                    )
                ) {
                    Text(stringResource(R.string.custom))
                }
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
                .weight(1f)
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

@Composable
fun DifficultySelectionDialog(
    onDifficultySelected: (Difficulty?) -> Unit,
    selectedDifficulty: CustomDifficulty,
) {
    var newDifficulty by remember { mutableStateOf(selectedDifficulty) }

    val minDim = 5
    val maxDim = 32

    val range = minDim.toFloat()..maxDim.toFloat()
    val steps = maxDim - minDim

    Dialog(onDismissRequest = { }) {
        Surface(
            modifier = Modifier.padding(10.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        valueRange = range,
                        value = newDifficulty.columns.toFloat(),
                        onValueChange = {
                            newDifficulty = newDifficulty.copy(columns = it.toInt())
                        },
                        modifier = Modifier.weight(1f),
                        steps = steps,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Gray,
                            inactiveTrackColor = Color.DarkGray,
                            activeTickColor = Color.Gray,
                            inactiveTickColor = Color.Gray
                        )
                    )
                    Text(text = stringResource(R.string.cols, newDifficulty.columns))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        valueRange = range,
                        value = newDifficulty.rows.toFloat(),
                        onValueChange = {
                            newDifficulty = newDifficulty.copy(rows = it.toInt())
                        },
                        modifier = Modifier.weight(1f),
                        steps = steps,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Gray,
                            inactiveTrackColor = Color.DarkGray,
                            activeTickColor = Color.Gray,
                            inactiveTickColor = Color.Gray
                        )
                    )
                    Text(text = stringResource(R.string.rows, newDifficulty.rows))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        valueRange = 1f..100f,
                        value = newDifficulty.minePercentage,
                        onValueChange = {
                            newDifficulty = newDifficulty.copy(minePercentage = it)
                        },
                        modifier = Modifier.weight(1f),
                        steps = 99,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Gray,
                            inactiveTrackColor = Color.DarkGray,
                            activeTickColor = Color.Gray,
                            inactiveTickColor = Color.Gray
                        )
                    )
                    Text(text = stringResource(R.string.mines, newDifficulty.minePercentage))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = { onDifficultySelected(newDifficulty) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                        )
                    ) {
                        Text(text = stringResource(R.string.accept))
                    }
                    Button(
                        onClick = { onDifficultySelected(null) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                        )
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }
        }
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
