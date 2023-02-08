package com.treefrogapps.androidx.compose.material

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import com.treefrogapps.androidx.compose.material.R.drawable
import com.treefrogapps.androidx.compose.material.R.string
import com.treefrogapps.androidx.compose.material.theme.Theme
import kotlinx.coroutines.launch


@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    labelText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {

    val hasError = remember(text) { error != null }

    Column(
        modifier = Modifier.bringIntoViewWhenFocused()
    ) {
        TextField(
            modifier = modifier,
            value = text,
            enabled = enabled,
            label = {
                Text(
                    text = labelText,
                    modifier = Modifier.padding(Theme.dimens.spacing.tiny))
            },
            onValueChange = { text ->
                onTextChanged(text)
                onErrorCleared()
            },
            trailingIcon = {
                error?.run {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = errorContentDescription,
                        tint = MaterialTheme.colors.error)
                }
            },
            singleLine = singleLine,
            maxLines = maxLines,
            isError = hasError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            shape = shape)
        ErrorText(
            error = error,
            hasError = hasError)
    }
}

@Composable
fun OutlinedTextInputField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    hasError: Boolean = false,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    labelText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {

    Column(
        modifier = Modifier.bringIntoViewWhenFocused()
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            enabled = enabled,
            label = {
                Text(
                    text = labelText,
                    modifier = Modifier.padding(Theme.dimens.spacing.tiny))
            },
            onValueChange = { text ->
                onTextChanged(text)
                onErrorCleared()
            },
            trailingIcon = {
                error?.run {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = errorContentDescription,
                        tint = MaterialTheme.colors.error)
                }
            },
            singleLine = singleLine,
            maxLines = maxLines,
            isError = hasError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            shape = shape)
        ErrorText(
            error = error,
            hasError = hasError)
    }
}

@Composable
fun OutlinedPasswordTextInputField(
    modifier: Modifier = Modifier,
    password: String,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    hasError: Boolean = false,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    labelText: String = "",
    enabled: Boolean = true
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.bringIntoViewWhenFocused()
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = password,
            enabled = enabled,
            label = {
                Text(
                    text = labelText,
                    modifier = Modifier.padding(Theme.dimens.spacing.tiny))
            },
            onValueChange = { text ->
                onTextChanged(text)
                onErrorCleared()
            },
            trailingIcon = {
                PasswordFieldTrailingIcon(
                    hasError = hasError,
                    enabled = enabled,
                    errorContentDescription = errorContentDescription,
                    passwordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = !passwordVisible })
            },
            singleLine = true,
            maxLines = 1,
            isError = hasError,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = shape,
            interactionSource = interactionSource)
        ErrorText(
            error = error,
            hasError = hasError)
    }
}

@Composable
fun PasswordTextInputField(
    modifier: Modifier = Modifier,
    password: String,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    labelText: String = "",
    enabled: Boolean = true,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val hasError = remember(password) { error != null }

    Column(
        modifier = Modifier.bringIntoViewWhenFocused()
    ) {
        TextField(
            modifier = modifier,
            value = password,
            enabled = enabled,
            label = {
                Text(
                    text = labelText,
                    modifier = Modifier.padding(Theme.dimens.spacing.tiny))
            },
            onValueChange = { text ->
                onTextChanged(text)
                onErrorCleared()
            },
            trailingIcon = {
                PasswordFieldTrailingIcon(
                    hasError = hasError,
                    enabled = enabled,
                    errorContentDescription = errorContentDescription,
                    passwordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = !passwordVisible })
            },
            singleLine = true,
            maxLines = 1,
            isError = hasError,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            shape = shape)
        ErrorText(
            error = error,
            hasError = hasError)
    }
}

@Composable
fun ErrorText(
    error: String?,
    hasError: Boolean,
    enter: EnterTransition = fadeIn() + expandVertically(),
    exit: ExitTransition = fadeOut() + shrinkVertically()
) {
    AnimatedVisibility(
        visible = hasError,
        enter = enter,
        exit = exit
    ) {
        Row {
            ErrorText(error = error)
        }
    }
}

@Composable
fun ErrorTextWithIcon(
    error: String?,
    hasError: Boolean,
    enter: EnterTransition = fadeIn() + expandVertically(),
    exit: ExitTransition = fadeOut() + shrinkVertically()
) {
    AnimatedVisibility(
        visible = hasError,
        enter = enter,
        exit = exit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Theme.dimens.spacing.normal,
                    end = Theme.dimens.spacing.normal,
                    bottom = Theme.dimens.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ErrorIcon()
            ErrorText(error = error)
        }
    }
}

@Composable
private fun ErrorText(error: String?) {
    Text(
        text = error ?: "",
        color = Theme.colors.error,
        style = Theme.typography.caption,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(
            start = Theme.dimens.spacing.small,
            end = Theme.dimens.spacing.small))
}

@Composable
private fun ErrorIcon() {
    Icon(
        painter = painterResource(id = drawable.ic_error_outline),
        tint = Theme.colors.error,
        contentDescription = stringResource(id = string.content_description_error))
}

@Composable
private fun PasswordFieldTrailingIcon(
    hasError: Boolean,
    errorContentDescription: String? = null,
    passwordVisible: Boolean,
    onVisibilityChange: () -> Unit,
    enabled: Boolean = true
) {
    if (hasError) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = errorContentDescription,
            tint = MaterialTheme.colors.error)
    } else IconButton(
        enabled = enabled,
        onClick = onVisibilityChange
    ) {
        Icon(
            painter = painterResource(id = if (passwordVisible) drawable.ic_visibility else drawable.ic_visibility_off),
            tint = Color.LightGray,
            contentDescription = stringResource(id = string.content_description_visibility))
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.bringIntoViewWhenFocused(): Modifier = composed {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    this.then(Modifier
        .onFocusChanged { fs ->
            if (fs.isFocused) coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
        }
        .bringIntoViewRequester(bringIntoViewRequester))
}
