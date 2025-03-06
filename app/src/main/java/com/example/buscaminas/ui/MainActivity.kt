package com.example.buscaminas.ui

import android.os.Bundle
import android.util.Log
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
import com.example.buscaminas.domain.DifficultySaver
import com.example.buscaminas.domain.EasyDifficulty
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selectedDifficulty: Difficulty by rememberSaveable (
                saver = DifficultySaver,
            ){
                mutableStateOf(EasyDifficulty) }

            var cells: List<List<CellState>> by rememberSaveable {
                mutableStateOf(resetGame(selectedDifficulty.rows, selectedDifficulty.columns, selectedDifficulty.numMines))
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
                minesRemaining = cells.flatten().count { it.isMine() } - cells.flatten().count { it is CellState.Flagged },
                time = seconds,
                userLooses = userLooses,
                userWins = userWins,
                cells = cells,
                selectedDifficulty = selectedDifficulty,
                onClick = {
                    cells = resetGame(selectedDifficulty.rows, selectedDifficulty.columns, selectedDifficulty.numMines)
                    seconds = 0
                },
                onCellClick = { rowIndex, colIndex ->
                    Log.d("xxy", "click row:$rowIndex column:$colIndex")
                    cells = revealCells(cells, rowIndex, colIndex)
                },
                onCellLongClick = { rowIndex, colIndex ->
                    Log.d("xxy", "long click row:$rowIndex column:$colIndex")
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

    private fun modifyCell(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int,
        newState: CellState
    ): List<List<CellState>> {
        if (cells[rowIndex][colIndex] == newState) return cells

        return cells.mapIndexed { r, row ->
            row.mapIndexed { c, cellState ->
                if (r == rowIndex && c == colIndex) newState else cellState
            }
        }
    }

    private fun resetGame(
        rows: Int,
        columns: Int,
        numMines: Int
    ): List<List<CellState>> {
        val emptyBoard = MutableList(rows) { MutableList(columns) { CellState.Hidden(hasMine = false) } }
        var minesPlaced = 0

        while (minesPlaced < numMines) {
            val randomRow = (0 until rows).random()
            val randomCol = (0 until columns).random()

            if (!emptyBoard[randomRow][randomCol].isMine()) {
                emptyBoard[randomRow][randomCol] = CellState.Hidden(hasMine = true)
                minesPlaced++
            }
        }
        return emptyBoard.map { it.toList() }.toList()
    }

    private fun revealCells(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int
    ): List<List<CellState>> {
        val newCell: CellState = when (val cell = cells[rowIndex][colIndex]) {
            is CellState.Hidden -> {
                if (cell.hasMine) {
                    CellState.MineExploded
                } else {
                    CellState.Visible(countMines(cells, rowIndex, colIndex))
                }
            }
            else -> cell
        }

        var newCells = modifyCell(cells, rowIndex, colIndex, newCell)

        if (newCell is CellState.MineExploded) {
            newCells = newCells.mapIndexed { row, rowList ->
                rowList.mapIndexed { col, currentCell ->
                    when {
                        row == rowIndex && col == colIndex -> CellState.MineExploded
                        currentCell is CellState.Hidden && currentCell.hasMine -> CellState.Mine
                        currentCell is CellState.Hidden -> CellState.Visible(countMines(cells, row, col))
                        else -> currentCell
                    }
                }
            }
        } else if (newCell is CellState.Visible && newCell.minesAround == 0) {
            val neighbours = findNeighboursIndex(cells, rowIndex, colIndex)
            neighbours.forEach { (row, col) ->
                if (cells[row][col] is CellState.Hidden) {
                    newCells = revealCells(newCells, row, col)
                }
            }
        }

        return newCells
    }

    private fun findNeighbours(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int
    ): List<CellState> {
        val neighbours = findNeighboursIndex(cells, rowIndex, colIndex)
        return neighbours.map { (row, col) -> cells[row][col] }
    }

    private fun findNeighboursIndex(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int
    ): List<Pair<Int, Int>> {
        val neighbours = mutableListOf<Pair<Int, Int>>()
        val offsets = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1, 0 to 1,
            1 to -1, 1 to 0, 1 to 1
        )
        for (offset in offsets) {
            val row = rowIndex + offset.first
            val col = colIndex + offset.second

            cells.getOrNull(row)?.getOrNull(col)?.let { neighbour ->
                neighbours.add(row to col)
            }
        }
        return neighbours
    }

    private fun countMines(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int
    ): Int {
        val neighbours = findNeighbours(cells, rowIndex, colIndex)
        return neighbours.count { it.isMine() }
    }

    private fun CellState.isMine() = this is CellState.Mine ||
            this is CellState.MineExploded ||
            this is CellState.Hidden && hasMine ||
            this is CellState.Flagged && hasMine
}

