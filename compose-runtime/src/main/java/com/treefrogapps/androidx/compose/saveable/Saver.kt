package com.treefrogapps.androidx.compose.saveable

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope

fun <T : Any> mutableStateSaver(): Saver<MutableState<T>, T> =
    object : Saver<MutableState<T>, T> {
        override fun restore(value: T): MutableState<T> = mutableStateOf(value)
        override fun SaverScope.save(value: MutableState<T>): T = value.value
    }

inline fun <reified E : Enum<E>> enumSaver() : Saver<Enum<E>, String> = object : Saver<Enum<E>, String> {
    override fun restore(value: String): Enum<E> = enumValueOf<E>(value)
    override fun SaverScope.save(value: Enum<E>): String = value.name
}