package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SevenSegmentDisplay(number: Int) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .border(2.dp, Color.Black)
    ) {
        number.coerceIn(0, 999)
            .toDigits()
            .let {
                List((3 - it.size).coerceAtLeast(0)) { 0 } + it
            }.forEach {
                SevenSegmentDigit(
                    digit = it,
                )
            }
    }
}

@Composable
private fun SevenSegmentDigit(
    digit: Int,
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    thickness: Dp = 4.dp
) {
    with(digit.toSegments()){
        Box(
            modifier = modifier
                .background(Color.DarkGray)
                .padding(3.dp)
                .width(size)
                .aspectRatio(.5f)
        ) {

            val horizontalModifier = Modifier
                .width(size - 1.dp)
                .padding(horizontal = thickness)
                .height(thickness)
            val verticalModifier = Modifier
                .height(size - 1.dp)
                .width(thickness)

            Segment(
                modifier = horizontalModifier
                    .align(Alignment.TopCenter),
                active = top,
            )
            Segment(
                modifier = verticalModifier
                    .align(Alignment.TopStart),
                active = topStart,
            )
            Segment(
                modifier = verticalModifier
                    .align(Alignment.TopEnd),
                active = topEnd,
            )
            Segment(
                modifier = horizontalModifier
                    .align(Alignment.Center),
                active = middle,
            )
            Segment(
                modifier = verticalModifier
                    .align(Alignment.BottomStart),
                active = bottomStart,
            )
            Segment(
                modifier = verticalModifier
                    .align(Alignment.BottomEnd),
                active = bottomEnd,
            )
            Segment(
                modifier = horizontalModifier
                    .align(Alignment.BottomCenter),
                active = bottom,
            )
        }
    }
    }

@Composable
private fun Int.toSegments() = when (this) {
    0 -> Segments(
        top = true,
        topStart = true,
        topEnd = true,
        bottomStart = true,
        bottomEnd = true,
        bottom = true
    )

    1 -> Segments(
        topEnd = true,
        bottomEnd = true
    )

    2 -> Segments(
        top = true,
        topEnd = true,
        middle = true,
        bottomStart = true,
        bottom = true
    )

    3 -> Segments(
        top = true,
        topEnd = true,
        middle = true,
        bottomEnd = true,
        bottom = true
    )

    4 -> Segments(
        topStart = true,
        topEnd = true,
        middle = true,
        bottomEnd = true
    )

    5 -> Segments(
        top = true,
        topStart = true,
        middle = true,
        bottomEnd = true,
        bottom = true
    )

    6 -> Segments(
        top = true,
        topStart = true,
        middle = true,
        bottomStart = true,
        bottomEnd = true,
        bottom = true
    )

    7 -> Segments(
        top = true,
        topEnd = true,
        bottomEnd = true
    )

    8 -> Segments(
        top = true,
        topStart = true,
        topEnd = true,
        middle = true,
        bottomStart = true,
        bottomEnd = true,
        bottom = true
    )

    9 -> Segments(
        top = true,
        topStart = true,
        topEnd = true,
        middle = true,
        bottomEnd = true,
        bottom = true
    )

    else -> {
        Segments()
    }
}

@Composable
private fun Segment(
    modifier: Modifier = Modifier,
    active: Boolean,
    activeColor: Color = Color.Red,
    inactiveColor: Color = Color.Red.copy(alpha = .2f),
) {
    Box(modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (active) activeColor else inactiveColor,
                    shape = RoundedCornerShape(25.dp)
                )
        )
    }
}

private data class Segments(
    val top: Boolean = false,
    val topStart: Boolean = false,
    val topEnd: Boolean = false,
    val middle: Boolean = false,
    val bottomStart: Boolean = false,
    val bottomEnd: Boolean = false,
    val bottom: Boolean = false
)

private fun Int.toDigits(): List<Int> {
    val digits = mutableListOf<Int>()
    var num = this
    while (num > 0) {
        digits.add(num % 10)
        num /= 10
    }
    return digits.reversed()
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun SevenSegmentDigitPreview() {
    FlowRow {
        for (digit in 0..9) {
            SevenSegmentDigit(
                digit = digit,
            )
        }
    }
}

@Preview
@Composable
fun SevenSegmentDigit2Preview() {
    LazyColumn {
        items((-1..1002).toList(), key = { it }) { n ->
            SevenSegmentDisplay(n)
        }
    }
}


