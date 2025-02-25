package com.example.buscaminas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

enum class CellState {
    HIDDEN, VISIBLE, MINE, MARKED
}

data class Cell(
    var state: CellState = CellState.HIDDEN,
    var minesAround: Int = 0
) {
    var stateObservable by mutableStateOf(state)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinesweeperScreen() {
    val size = 6
    var gameOver by remember { mutableStateOf(false) }
    val board = remember { mutableStateListOf<Cell>() }

    fun resetGame() {
        gameOver = false
        board.clear()
        repeat(size * size) {
            board.add(Cell())
        }
        generateMines(board, size, 5)
    }

    LaunchedEffect(Unit) {
        resetGame()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Mines: ", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Time: ", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(size),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.size((size * 40).dp)
            ) {
                itemsIndexed(board) { index, cell ->
                    CellButton(
                        cell = cell,
                        onClick = {
                            if (!gameOver) {
                                if (cell.stateObservable == CellState.MINE) {
                                    gameOver = true
                                    revealMines(board)
                                } else {
                                    handleCellClick(cell)
                                }
                            }
                        },
                        onLongClick = { if (!gameOver) toggleCellMark(cell) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { resetGame() }) {
                Text(text = "Restart", fontSize = 18.sp)
            }

            if (gameOver) {
                Text(
                    text = "Game Over!",
                    color = Color.Red,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CellButton(cell: Cell, onClick: () -> Unit, onLongClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onClick()
                        tryAwaitRelease()
                    },
                    onLongPress = { onLongClick() }
                )
            },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (cell.stateObservable) {
                CellState.VISIBLE -> Color.White
                CellState.MINE -> if (cell.stateObservable == CellState.VISIBLE) Color.Red else Color.Blue
                CellState.MARKED -> Color.Yellow
                else -> Color.Blue
            }
        )
    ) {
        Text(
            text = when (cell.stateObservable) {
                CellState.MINE -> if (cell.stateObservable == CellState.VISIBLE) "X" else ""
                CellState.VISIBLE -> if (cell.minesAround > 0) cell.minesAround.toString() else ""
                CellState.MARKED -> "F"
                else -> ""
            },
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

fun generateMines(board: MutableList<Cell>, size: Int, mineCount: Int) {
    val random = Random
    var placedMines = 0

    while (placedMines < mineCount) {
        val index = random.nextInt(board.size)

        if (board[index].stateObservable != CellState.MINE) {
            board[index].stateObservable = CellState.MINE
            placedMines++
        }
    }
}

fun handleCellClick(cell: Cell) {
    if (cell.stateObservable == CellState.HIDDEN) {
        cell.stateObservable = CellState.VISIBLE
    }
}

fun toggleCellMark(cell: Cell) {
    when (cell.stateObservable) {
        CellState.HIDDEN -> cell.stateObservable = CellState.MARKED
        CellState.MARKED -> cell.stateObservable = CellState.HIDDEN
        else -> return
    }
}

fun revealMines(board: List<Cell>) {
    board.forEach { cell ->
        if (cell.stateObservable == CellState.MINE) {
            cell.stateObservable = CellState.VISIBLE
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MinesweeperScreen()
}
