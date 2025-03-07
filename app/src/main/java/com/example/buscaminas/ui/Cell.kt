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
    val cellText = cellState.content()

    val cellColor = when (cellState) {
        is CellState.Hidden, is CellState.Flagged -> Color.DarkGray
        is CellState.Visible, is CellState.Mine, is CellState.MineExploded -> Color.LightGray
    }

    val textColor = when (cellState) {
        is CellState.Flagged -> Color.Red
        is CellState.Visible -> getTextColorBasedOnMines(cellState.minesAround)
        else -> Color.Black
    }

    Box(
        modifier = Modifier
            .size(41.dp)
            .border(1.dp, Color.White)
            .background(cellColor)
            .aspectRatio(1f)
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    onLongClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (cellState is CellState.Mine) {
            Image(
                painter = painterResource(id = R.drawable.mine),
                contentDescription = "Mine",
                modifier = Modifier.padding(5.dp)

            )
        } else if (cellState is CellState.MineExploded) {
            Image(
                painter = painterResource(id = R.drawable.explosion),
                contentDescription = "MineExploded",
                modifier = Modifier.padding(1.dp)

            )
        }  else if (cellState is CellState.Flagged) {
        Image(
            painter = painterResource(id = R.drawable.flag),
            contentDescription = "Flagged",
            modifier = Modifier.padding(1.dp)

        )
    }

        Text(
            text = cellText,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun CellState.content(): String {
    return when (this) {
        is CellState.Hidden -> " "
        is CellState.Visible -> if (this.minesAround == 0) " " else this.minesAround.toString()
        is CellState.Flagged -> " "
        is CellState.Mine -> " "
        is CellState.MineExploded -> ""
    }
}

@Composable
private fun getTextColorBasedOnMines(minesAround: Int): Color {
    return when (minesAround) {
        1 -> Color.Blue
        2 -> Color.Green
        3 -> Color.Red
        4 -> Color.Magenta
        5 -> Color.Yellow
        6 -> Color.Cyan
        7 -> Color.Black
        8 -> Color.DarkGray
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