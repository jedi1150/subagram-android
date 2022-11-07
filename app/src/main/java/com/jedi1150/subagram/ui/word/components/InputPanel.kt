package com.jedi1150.subagram.ui.word.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jedi1150.subagram.R
import com.jedi1150.subagram.ui.word.AnagramError
import com.jedi1150.subagram.ui.word.WordUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun InputPanel(
    modifier: Modifier = Modifier,
    uiState: WordUiState = WordUiState(),
    onInputChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val layoutDirection = LocalLayoutDirection.current

    Row(
        modifier = modifier
            .padding(8.dp)
            .padding(
                bottom = maxOf(contentPadding.calculateBottomPadding(),
                    WindowInsets.ime
                        .asPaddingValues()
                        .calculateBottomPadding()),
                start = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection) + WindowInsets.displayCutout
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection) + WindowInsets.displayCutout
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        OutlinedTextField(
            value = uiState.input,
            onValueChange = { value ->
                onInputChanged(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = {
                Text(text = stringResource(R.string.anagram_placeholder))
            },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    AnimatedContent(
                        targetState = uiState.error,
                        transitionSpec = {
                            if (targetState != null && initialState == null) {
                                slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                            }.using(SizeTransform(clip = false))
                        },
                    ) { target ->
                        when (target) {
                            AnagramError.EMPTY -> Text(stringResource(R.string.anagram_error_empty))
                            AnagramError.SHORT -> Text(stringResource(R.string.anagram_error_short))
                            AnagramError.SAME -> Text(stringResource(R.string.anagram_error_same))
                            AnagramError.NOT_SINGLE -> Text(stringResource(R.string.anagram_error_not_single))
                            AnagramError.NOT_ANAGRAM -> Text(stringResource(R.string.anagram_error_not_anagram))
                            AnagramError.ALREADY_EXISTS -> Text(stringResource(R.string.anagram_error_already_exists))
                            null -> Text(text = stringResource(R.string.anagram_support))
                        }
                    }
                    AnimatedContent(
                        targetState = uiState.input.length,
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                            }.using(SizeTransform(clip = false))
                        },
                    ) { targetCount ->
                        Text(text = targetCount.toString())
                    }
                }
            },
            trailingIcon = {
                Crossfade(targetState = uiState.input.isNotEmpty()) { value ->
                    if (value) {
                        IconButton(onClick = { onInputChanged(String()) }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        }
                    }
                }
            },
            isError = uiState.error != null,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, autoCorrect = false, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onSubmitClicked() }),
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge,
        )
        FilledTonalButton(
            onClick = {
                onSubmitClicked()
            },
            modifier = Modifier
                .height(59.dp)
                .padding(top = 8.dp),
        ) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}

@Preview
@Composable
private fun PreviewInputPanel() {
    InputPanel(
        onInputChanged = {},
        onSubmitClicked = {},
    )
}
