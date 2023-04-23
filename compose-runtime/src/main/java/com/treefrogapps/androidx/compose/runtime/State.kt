package com.treefrogapps.androidx.compose.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.treefrogapps.androidx.compose.saveable.mutableStateSaver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <T : Any> MutableStateFlow<T>.collectAsMutableState(
    key1: Any? = null,
    key2: Any? = null
): MutableState<T> =
    remember(key1 = key1, key2 = key2) { mutableStateOf(value) }
        .apply {
            LaunchedEffect(key1 = this, key2 = this@collectAsMutableState) {
                this@collectAsMutableState
                    .onEach { value = it }
                    .launchIn(this)
                snapshotFlow { value }
                    .onEach { this@collectAsMutableState.value = it }
                    .launchIn(this)
            }
        }

@Composable
fun <T : Any> MutableStateFlow<T?>.collectAsNullableMutableState(
    key1: Any? = null,
    key2: Any? = null
): MutableState<T?> =
    remember(key1 = key1, key2 = key2) { mutableStateOf(value) }
        .apply {
            LaunchedEffect(key1 = this, key2 = this@collectAsNullableMutableState) {
                this@collectAsNullableMutableState
                    .onEach { value = it }
                    .launchIn(this)
                snapshotFlow { value }
                    .onEach { this@collectAsNullableMutableState.value = it }
                    .launchIn(this)
            }
        }

@Composable
fun MutableStateFlow<String>.collectAsTextFieldValueMutableState(
    key1: Any? = null,
    key2: Any? = null
): MutableState<TextFieldValue> =
    remember(key1 = key1, key2 = key2) { mutableStateOf(TextFieldValue(text = value, selection = TextRange(index = value.length))) }
        .apply {
            LaunchedEffect(key1 = this, key2 = this@collectAsTextFieldValueMutableState) {
                this@collectAsTextFieldValueMutableState
                    .onEach { value = value.copy(text = it) }
                    .launchIn(this)
                snapshotFlow { value }
                    .onEach { this@collectAsTextFieldValueMutableState.value = it.text }
                    .launchIn(this)
            }
        }


@Composable
fun <T> rememberMutableState(
    initial: T,
    key1: Any? = null,
    key2: Any? = null,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): MutableState<T> =
    remember(key1, key2) {
        mutableStateOf(value = initial, policy = policy)
    }

@Composable
fun <T> rememberMutableState(
    key1: Any? = null,
    key2: Any? = null,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    initialProvider: () -> T
): MutableState<T> =
    remember(key1, key2) {
        mutableStateOf(value = initialProvider(), policy = policy)
    }

@Composable
fun <T> rememberState(
    initial: T,
    key1: Any? = null,
    key2: Any? = null,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): State<T> =
    remember(key1, key2) {
        mutableStateOf(value = initial, policy = policy)
    }

@Composable
fun <T> rememberState(
    key1: Any? = null,
    key2: Any? = null,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    initial: () -> T
): State<T> =
    remember(key1, key2) {
        mutableStateOf(value = initial(), policy = policy)
    }

@Composable
fun <T : Any> rememberSavableMutableState(
    initial: T,
    key1: Any? = null,
    key2: Any? = null,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<MutableState<T>, out Any> = mutableStateSaver()
): MutableState<T> =
    rememberSaveable(key1, key2, saver = saver) {
        mutableStateOf(value = initial, policy = policy)
    }

@Composable
fun <T> rememberNullableMutableState(
    initial: T? = null,
    key1: Any? = null,
    key2: Any? = null,
    policy: SnapshotMutationPolicy<T?> = structuralEqualityPolicy()
): MutableState<T?> =
    remember(key1, key2) {
        mutableStateOf(value = initial, policy = policy)
    }

@Composable
fun <T> rememberDerivedStateOf(
    key1: Any? = null,
    key2: Any? = null,
    calculation: () -> T
): State<T> = remember(key1, key2) { derivedStateOf(calculation) }


fun <T> MutableState<T>.getAndUpdate(block: (T) -> T) {
    value = block(value)
}

fun <T> MutableState<T>.set(t: T) {
    value = t
}

fun MutableState<Boolean>.toggle() {
    value = !value
}