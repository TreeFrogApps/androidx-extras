package com.treefrogapps.androidx.compose.material3

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.treefrogapps.androidx.compose.material3.R.drawable
import com.treefrogapps.androidx.compose.material3.R.string
import com.treefrogapps.androidx.compose.material3.theme.Theme
import com.treefrogapps.androidx.compose.ui.verticalScrolling


@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    labelText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    val hasError = error != null

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        textStyle = textStyle,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { value ->
            onTextChanged(value)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = @Composable {
            if (hasError) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = errorContentDescription,
                    tint = Theme.colorScheme.error
                )
            } else {
                trailingIcon?.invoke()
            }
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
        isError = hasError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )

}


@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    textStyle: TextStyle = LocalTextStyle.current,
    onTextChanged: (TextFieldValue) -> Unit,
    error: String? = null,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    labelText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    val hasError = error != null

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldValue,
        textStyle = textStyle,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { text ->
            onTextChanged(text)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = @Composable {
            if (hasError) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = errorContentDescription,
                    tint = Theme.colorScheme.error
                )
            } else {
                trailingIcon?.invoke()
            }
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
        isError = hasError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}


@Composable
fun OutlinedTextInputField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    textStyle: TextStyle = LocalTextStyle.current,
    onTextChanged: (TextFieldValue) -> Unit,
    error: String? = null,
    hasError: Boolean = false,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    labelText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldValue,
        textStyle = textStyle,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { text ->
            onTextChanged(text)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = {
            if (hasError) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = errorContentDescription,
                    tint = Theme.colorScheme.error
                )
            } else {
                trailingIcon?.invoke()
            }
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
        isError = hasError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

@Composable
fun OutlinedTextInputField(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    hasError: Boolean = false,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    labelText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        textStyle = textStyle,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { value ->
            onTextChanged(value)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = @Composable {
            if (hasError) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = errorContentDescription,
                    tint = Theme.colorScheme.error
                )
            } else {
                trailingIcon?.invoke()
            }
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
        isError = hasError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

@Composable
fun OutlinedPasswordTextInputField(
    modifier: Modifier = Modifier,
    passwordValue: TextFieldValue,
    textStyle: TextStyle,
    onTextChanged: (TextFieldValue) -> Unit,
    error: String? = null,
    hasError: Boolean = false,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    labelText: String = "",
    enabled: Boolean = true,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = passwordValue,
        textStyle = textStyle,
        enabled = enabled,
        label = @Composable {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { text ->
            onTextChanged(text)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = @Composable {
            PasswordFieldTrailingIcon(
                hasError = hasError,
                enabled = enabled,
                errorContentDescription = errorContentDescription,
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible })
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = true,
        maxLines = 1,
        isError = hasError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = shape,
        interactionSource = interactionSource,
        colors = colors
    )
}

@Composable
fun OutlinedPasswordTextInputField(
    modifier: Modifier = Modifier,
    password: String,
    textStyle: TextStyle,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    hasError: Boolean = false,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    labelText: String = "",
    enabled: Boolean = true,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        textStyle = textStyle,
        enabled = enabled,
        label = @Composable {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { text ->
            onTextChanged(text)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = @Composable {
            PasswordFieldTrailingIcon(
                hasError = hasError,
                enabled = enabled,
                errorContentDescription = errorContentDescription,
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible })
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = true,
        maxLines = 1,
        isError = hasError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = shape,
        interactionSource = interactionSource,
        colors = colors
    )
}

@Composable
fun PasswordTextInputField(
    modifier: Modifier = Modifier,
    passwordValue: TextFieldValue,
    textStyle: TextStyle,
    onTextChanged: (TextFieldValue) -> Unit,
    error: String? = null,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    labelText: String = "",
    enabled: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val hasError = error != null

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = passwordValue,
        textStyle = textStyle,
        enabled = enabled,
        label = @Composable {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { text ->
            onTextChanged(text)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = @Composable {
            PasswordFieldTrailingIcon(
                hasError = hasError,
                enabled = enabled,
                errorContentDescription = errorContentDescription,
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible })
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = true,
        maxLines = 1,
        isError = hasError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

@Composable
fun PasswordTextInputField(
    modifier: Modifier = Modifier,
    password: String,
    textStyle: TextStyle,
    onTextChanged: (String) -> Unit,
    error: String? = null,
    onErrorCleared: () -> Unit = {},
    errorContentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    labelText: String = "",
    enabled: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val hasError = error != null

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        textStyle = textStyle,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                modifier = Modifier.padding(Theme.dimens.spacing.tiny)
            )
        },
        onValueChange = { text ->
            onTextChanged(text)
            onErrorCleared()
        },
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        trailingIcon = {
            PasswordFieldTrailingIcon(
                hasError = hasError,
                enabled = enabled,
                errorContentDescription = errorContentDescription,
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible })
        },
        supportingText = @Composable {
            ErrorText(
                error = error,
                hasError = hasError
            )
        },
        singleLine = true,
        maxLines = 1,
        isError = hasError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
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
                    bottom = Theme.dimens.spacing.small
                ),
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
        text = error.orEmpty(),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ErrorIcon() {
    Icon(
        painter = painterResource(id = drawable.ic_error_outline),
        tint = Theme.colorScheme.error,
        contentDescription = stringResource(id = string.content_description_error)
    )
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
            tint = Theme.colorScheme.error
        )
    } else IconButton(
        enabled = enabled,
        onClick = onVisibilityChange
    ) {
        Icon(
            painter = painterResource(id = if (passwordVisible) drawable.ic_visibility else drawable.ic_visibility_off),
            tint = Color.LightGray,
            contentDescription = stringResource(id = string.content_description_visibility)
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun TextFieldPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .verticalScrolling()
                    .padding(paddingValues = innerPadding)
                    .padding(all = Theme.dimens.spacing.large)
            ) {
                var text by remember { mutableStateOf(value = "") }
                val actions = KeyboardActions(onAny = {
                    defaultKeyboardAction(imeAction = ImeAction.Next)
                })

                TextInputField(
                    labelText = "Some label",
                    text = text,
                    keyboardActions = actions,
                    onTextChanged = { text = it }
                )

                TextInputField(
                    labelText = "Some label",
                    text = text,
                    keyboardActions = actions,
                    onTextChanged = { text = it },
                    error = "Some error"
                )

                OutlinedTextInputField(
                    labelText = "Some label",
                    text = text,
                    keyboardActions = actions,
                    onTextChanged = { text = it }
                )

                OutlinedTextInputField(
                    labelText = "Some label",
                    text = text,
                    keyboardActions = actions,
                    onTextChanged = { text = it },
                    hasError = true,
                    error = "Some error"
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun TextFieldPreviewDark(modifier: Modifier = Modifier) {

}
