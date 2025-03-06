package com.example.buscaminas.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import com.example.buscaminas.domain.Difficulty
import com.example.buscaminas.domain.EasyDifficulty
import com.example.buscaminas.domain.HardDifficulty
import com.example.buscaminas.domain.MediumDifficulty

object DifficultySaver : Saver<MutableState<Difficulty>, String> {
    override fun restore(value: String) = mutableStateOf(
        when (value) {
            EasyDifficulty::class.simpleName.orEmpty() -> EasyDifficulty
            MediumDifficulty::class.simpleName.orEmpty() -> MediumDifficulty
            HardDifficulty::class.simpleName.orEmpty() -> HardDifficulty
            else -> error("Invalid difficulty: $value")
        }
    )

    override fun SaverScope.save(value: MutableState<Difficulty>) =
        value.value::class.simpleName.orEmpty()
}