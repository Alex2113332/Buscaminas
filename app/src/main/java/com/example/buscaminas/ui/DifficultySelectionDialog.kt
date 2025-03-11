package com.example.buscaminas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.buscaminas.R
import com.example.buscaminas.domain.CustomDifficulty
import com.example.buscaminas.domain.Difficulty

@Composable
fun DifficultySelectionDialog(
    onDifficultySelected: (Difficulty?) -> Unit,
    selectedDifficulty: CustomDifficulty,
) {
    var newDifficulty by remember { mutableStateOf(selectedDifficulty) }

    val minDim = 5
    val maxDim = 32

    val range = minDim.toFloat()..maxDim.toFloat()
    val steps = maxDim - minDim

    Dialog(onDismissRequest = { }) {
        Surface(
            modifier = Modifier.padding(10.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        valueRange = range,
                        value = newDifficulty.columns.toFloat(),
                        onValueChange = {
                            newDifficulty = newDifficulty.copy(columns = it.toInt())
                        },
                        modifier = Modifier.weight(1f),
                        steps = steps,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Gray,
                            inactiveTrackColor = Color.DarkGray,
                            activeTickColor = Color.Gray,
                            inactiveTickColor = Color.Gray
                        )
                    )
                    Text(text = stringResource(R.string.cols, newDifficulty.columns))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        valueRange = range,
                        value = newDifficulty.rows.toFloat(),
                        onValueChange = {
                            newDifficulty = newDifficulty.copy(rows = it.toInt())
                        },
                        modifier = Modifier.weight(1f),
                        steps = steps,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Gray,
                            inactiveTrackColor = Color.DarkGray,
                            activeTickColor = Color.Gray,
                            inactiveTickColor = Color.Gray
                        )
                    )
                    Text(text = stringResource(R.string.rows, newDifficulty.rows))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        valueRange = 1f..99f,
                        value = newDifficulty.minePercentage,
                        onValueChange = {
                            newDifficulty = newDifficulty.copy(minePercentage = it)
                        },
                        modifier = Modifier.weight(1f),
                        steps = 99,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Gray,
                            inactiveTrackColor = Color.DarkGray,
                            activeTickColor = Color.Gray,
                            inactiveTickColor = Color.Gray
                        )
                    )
                    Text(text = stringResource(R.string.mines, newDifficulty.minePercentage))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = { onDifficultySelected(newDifficulty) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                        )
                    ) {
                        Text(text = stringResource(R.string.accept))
                    }
                    Button(
                        onClick = { onDifficultySelected(null) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                        )
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DifficultySelectionDialogPreview() {
    DifficultySelectionDialog(
        onDifficultySelected = {},
        selectedDifficulty = CustomDifficulty(10, 15, 30f)
    )
}

