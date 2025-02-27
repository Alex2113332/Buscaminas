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
                    cells = modifyCell(cells, rowIndex, colIndex, CellState.Visible(0))
                }
            )
        }
    }
    
    private fun modifyCell(
        cells: List<List<CellState>>,
        rowIndex: Int,
        colIndex: Int,
        newState: CellState.Visible
    ) = cells.mapIndexed { r, row ->
        row.mapIndexed { c, cellState ->
            if (r == rowIndex && c == colIndex) {
                newState
            } else {
                cellState
            }
        }
    }

    private fun resetGame(
        rows: Int,
        columns: Int
    ): List<List<CellState>> {
        return List(rows) { List(columns) { CellState.Hidden } }
    }

}


