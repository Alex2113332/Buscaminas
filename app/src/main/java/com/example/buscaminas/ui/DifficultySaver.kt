package com.example.buscaminas.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import com.example.buscaminas.domain.CustomDifficulty
import com.example.buscaminas.domain.Difficulty
import com.example.buscaminas.domain.EasyDifficulty
import com.example.buscaminas.domain.HardDifficulty
import com.example.buscaminas.domain.MediumDifficulty

private const val TYPE = "type"
private const val EASY = "easy"
private const val MEDIUM = "medium"
private const val HARD = "hard"
private const val ROWS = "rows"
private const val COLS = "cols"
private const val PERCENT = "percent"

val DifficultySaver: Saver<MutableState<Difficulty>, Any> = mapSaver(
    save = {
        when (val difficulty = it.value) {
            is EasyDifficulty -> mapOf(TYPE to EASY)
            is MediumDifficulty -> mapOf(TYPE to MEDIUM)
            is HardDifficulty -> mapOf(TYPE to HARD)
            is CustomDifficulty -> mapOf(
                ROWS to difficulty.rows,
                COLS to difficulty.columns,
                PERCENT to difficulty.minePercentage,
            )
        }
    },
    restore = { data ->
        mutableStateOf(
            when (data[TYPE]) {
                EASY -> EasyDifficulty
                MEDIUM -> MediumDifficulty
                HARD -> HardDifficulty
                null -> CustomDifficulty(
                    rows = (data[ROWS] as Int),
                    columns = (data[COLS] as Int),
                    minePercentage = (data[PERCENT] as Float)
                )

                else -> error("Unknown difficulty type")
            }
        )
    }
)