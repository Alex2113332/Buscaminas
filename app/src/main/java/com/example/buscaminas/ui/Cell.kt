package com.example.buscaminas.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buscaminas.R
import com.example.buscaminas.domain.CellState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Cell(
    cellState: CellState,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    val cellColor = when (cellState) {
        is CellState.Hidden, is CellState.Flagged -> Color(0xFF4A4A4A)
        is CellState.Visible, is CellState.Mine, is CellState.MineExploded -> {
            if (cellState is CellState.MineExploded) Color(0xFFFF4C4C)
            else Color(0xFFD3D3D3)
        }
    }

    Box(
        modifier = Modifier
            .size(41.dp)
            .background(cellColor, shape = MaterialTheme.shapes.medium)
            .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
            .aspectRatio(1f)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        contentAlignment = Alignment.Center
    ) {
        val cellContent = when (cellState) {
            is CellState.Mine -> R.drawable.mine3
            is CellState.MineExploded -> R.drawable.explosion3
            is CellState.Flagged -> R.drawable.flag
            else -> null
        }

        cellContent?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Cell Image",
                modifier = Modifier.padding(5.dp)
            )
        }

        if (cellState is CellState.Visible) {
            Text(
                text = if (cellState.minesAround == 0) " " else cellState.minesAround.toString(),
                color = getTextColorBasedOnMines(cellState.minesAround),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
private fun getTextColorBasedOnMines(minesAround: Int): Color {
    return when (minesAround) {
        1 -> Color(0xFF1E90FF)
        2 -> Color(0xFF32CD32)
        3 -> Color(0xFFFF6347)
        4 -> Color(0xFF800080)
        5 -> Color(0xFFFFD700)
        6 -> Color(0xFF00CED1)
        7 -> Color(0xFF000000)
        8 -> Color(0xFF696969)
        else -> Color.Black
    }
}

@Preview
@Composable
fun CellHiddenPreview() {
    Cell(
        cellState = CellState.Hidden(hasMine = false),
    )
}

@Preview
@Composable
fun CellVisiblePreview() {
    Cell(
        cellState = CellState.Visible(3),
    )
}

@Preview
@Composable
fun CellFlaggedPreview() {
    Cell(
        cellState = CellState.Flagged(hasMine = false),
    )
}

@Preview
@Composable
fun CellMinePreview() {
    Cell(
        cellState = CellState.Mine,
    )
}

@Preview
@Composable
fun CellMineExplodedPreview() {
    Cell(
        cellState = CellState.MineExploded,
    )
}