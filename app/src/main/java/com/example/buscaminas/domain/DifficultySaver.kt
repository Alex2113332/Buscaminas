package com.example.buscaminas.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope

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