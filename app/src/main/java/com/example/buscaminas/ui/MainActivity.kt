package com.example.buscaminas.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.buscaminas.domain.CellState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rows = 6
        val columns = 7
        setContent {
            var cells: List<List<CellState>> by remember {
                mutableStateOf(resetGame(rows, columns))
            }

            MinesweeperScreen(
                3,
                6,
                cells = cells,
                onClick = {
                    cells = resetGame(rows, columns)
                },
                onCellClick = { rowIndex, colIndex ->
                    Log.d("xxy", "click row:$rowIndex column:$colIndex")
                    cells = revealCells(cells, rowIndex, colIndex)
                }
            )
        }
    }

    private fun modifyCell(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int,
        newState: CellState
    ): List<List<CellState>> {
        return cells.mapIndexed { r, row ->
            row.mapIndexed { c, cellState ->
                if (r == rowIndex && c == colIndex) {
                    newState
                } else {
                    cellState
                }
            }
        }
    }

    private fun resetGame(
        rows: Int,
        columns: Int
    ): List<List<CellState>> {
        val emptyBoard = List(rows) { List(columns) { CellState.Hidden(hasMine = false) } }
        return modifyCell(
        modifyCell(emptyBoard, 1, 1, CellState.Hidden(hasMine = true)),
        4, 5, CellState.Hidden(hasMine = true)
        )
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
        }

        return newCells

    }

    private fun countMines(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int
    ): Int {
        var minesAround = 0

        val offsets = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1, 0 to 1,
            1 to -1, 1 to 0, 1 to 1
        )
        for (offset in offsets) {
            val row = rowIndex + offset.first
            val col = colIndex + offset.second

            cells.getOrNull(row)?.getOrNull(col)?.let { neighbour ->
                if (neighbour is CellState.Mine ||
                    neighbour is CellState.MineExploded ||
                    neighbour is CellState.Hidden && neighbour.hasMine ||
                    neighbour is CellState.Flagged && neighbour.hasMine
                ) {
                    minesAround++
                }
            }
        }

        return minesAround

    }
}

