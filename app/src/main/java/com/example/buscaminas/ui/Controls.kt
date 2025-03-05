package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Controls(
    minesRemaining: Int,
    time: Int,
    userLooses: Boolean = false,
    userWins: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mines: $minesRemaining"
        )
        IconButton(onClick = onClick) {
            if (userLooses) {
                Icon(
                    imageVector = Icons.Filled.SentimentDissatisfied,
                    contentDescription = "Sad face",
                    tint = Color.Black
                )
            } else if (userWins) {
                Icon(
                    imageVector = Icons.Filled.SentimentSatisfied,
                    contentDescription = "Happy face",
                    tint = Color.Black
                )
            }
            else {
                Icon(
                    imageVector = Icons.Filled.SentimentNeutral,
                    contentDescription = "Happy face",
                    tint = Color.Black
                )
            }
        }
        Text(text = "Time: $time")
    }
}

@Preview
@Composable
fun ControlsPreview() {
    Controls(minesRemaining = 5, time = 0, onClick = {})

}