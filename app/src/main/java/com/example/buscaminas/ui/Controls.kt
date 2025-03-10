package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Controls(
    minesRemaining: Int,
    time: Int,
    userLooses: Boolean = false,
    userWins: Boolean = false,
    onClick: () -> Unit = {}
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(
                color = when {
                    userLooses -> Color.Red.copy(alpha = 0.1f)
                    userWins -> Color.Green.copy(alpha = 0.1f)
                    else -> Color.Gray.copy(alpha = 0.2f)
                })
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        maxItemsInEachRow = 3

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Mines: $minesRemaining",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = when {
                            userLooses -> Color.Red.copy(alpha = 0.1f)
                            userWins -> Color.Green.copy(alpha = 0.1f)
                            else -> Color.Transparent
                        },
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(8.dp)
            ) {
                val icon = when {
                    userLooses -> Icons.Filled.SentimentVeryDissatisfied
                    userWins -> Icons.Filled.SentimentVerySatisfied
                    else -> Icons.Filled.SentimentNeutral
                }

                Icon(
                    imageVector = icon,
                    contentDescription = "Game Status",
                    tint = Color.Black,
                    modifier = Modifier.size(48.dp)
                )
            }

            Text(
                text = "Time: $time",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ControlsPreview() {
    Controls(minesRemaining = 5, time = 0, onClick = {})

}