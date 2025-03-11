package com.example.buscaminas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buscaminas.R
import com.example.buscaminas.domain.CustomDifficulty
import com.example.buscaminas.domain.Difficulty
import com.example.buscaminas.domain.EasyDifficulty
import com.example.buscaminas.domain.HardDifficulty
import com.example.buscaminas.domain.MediumDifficulty

@Composable
fun DifficultySelector(
    onDifficultySelected: (Difficulty) -> Unit,
    selectedDifficulty: Difficulty,
    onCustomDifficultySelected: () -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(Color.Gray.copy(alpha = 0.2f))
            .border(2.dp, Color.Black, shape = MaterialTheme.shapes.large)
            .padding(10.dp),
    ) {
        items(
            listOf(
                EasyDifficulty to R.string.Easy,
                MediumDifficulty to R.string.Medium,
                HardDifficulty to R.string.Hard
            )
        ) { (difficulty, stringRes) ->
            Button(
                onClick = { onDifficultySelected(difficulty) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDifficulty == difficulty) Color.Black else Color.Gray,
                )
            ) {
                Text(stringResource(stringRes))
            }
        }

        item {
            Button(
                onClick = onCustomDifficultySelected,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDifficulty is CustomDifficulty) Color.Black else Color.Gray,
                )
            ) {
                Text(stringResource(R.string.custom))
            }
        }
    }
}

@Preview
@Composable
fun DifficultySelectorPreview() {
    DifficultySelector(
        onDifficultySelected = { },
        selectedDifficulty = EasyDifficulty,
        onCustomDifficultySelected = { }
    )
}

