package com.example.buscaminas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.buscaminas.domain.CellState
import com.example.buscaminas.domain.Difficulty
import com.example.buscaminas.domain.EasyDifficulty
import com.example.buscaminas.domain.isMine
import com.example.buscaminas.domain.modifyCell
import com.example.buscaminas.domain.resetGame
import com.example.buscaminas.domain.revealCells
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selectedDifficulty: Difficulty by rememberSaveable(
                saver = DifficultySaver,
            ) {
                mutableStateOf(EasyDifficulty)
            }

            var cells: List<List<CellState>> by rememberSaveable {
                mutableStateOf(
                    resetGame(
                        selectedDifficulty.rows,
                        selectedDifficulty.columns,
                        selectedDifficulty.numMines
                    )
                )
            }

            var seconds by rememberSaveable { mutableIntStateOf(0) }

            val flattenCells = cells.flatten()

            val userLooses = flattenCells.any { it is CellState.MineExploded }

            val userWins = flattenCells.all {
                when (it) {
                    is CellState.Visible -> true
                    is CellState.Flagged -> it.hasMine
                    is CellState.Hidden -> it.hasMine
                    else -> false
                }
            }

            MinesweeperScreen(
                minesRemaining = flattenCells.count { it.isMine() } - flattenCells.count { it is CellState.Flagged },
                time = seconds,
                userLooses = userLooses,
                userWins = userWins,
                cells = cells,
                selectedDifficulty = selectedDifficulty,
                onClick = {
                    cells = resetGame(
                        selectedDifficulty.rows,
                        selectedDifficulty.columns,
                        selectedDifficulty.numMines
                    )
                    seconds = 0
                },
                onCellClick = { rowIndex, colIndex ->
                    cells = revealCells(cells, rowIndex, colIndex)
                },
                onCellLongClick = { rowIndex, colIndex ->
                    val newCellState = when (val currentCell = cells[rowIndex][colIndex]) {
                        is CellState.Hidden -> {
                            CellState.Flagged(hasMine = currentCell.hasMine)
                        }

                        is CellState.Flagged -> {
                            CellState.Hidden(hasMine = currentCell.hasMine)
                        }

                        else -> currentCell
                    }
                    cells = modifyCell(cells, rowIndex, colIndex, newCellState)
                },
                onDifficultySelected = { difficulty ->
                    selectedDifficulty = difficulty
                    cells = resetGame(difficulty.rows, difficulty.columns, difficulty.numMines)
                    seconds = 0
                }
            )

            LaunchedEffect(seconds) {
                if (!userLooses && !userWins) {
                    delay(1000)
                    seconds++
                }

            }
        }
    }
}