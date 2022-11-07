package com.jedi1150.subagram.ui.createWord

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CreateWordScreen(
    createWordState: CreateWordState,
    contentPadding: PaddingValues = PaddingValues(),
    onInputChanged: (String) -> Unit,
    onCreateClicked: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(PaddingValues(
                    start = WindowInsets.displayCutout
                        .asPaddingValues()
                        .calculateStartPadding(layoutDirection),
                    end = WindowInsets.displayCutout
                        .asPaddingValues()
                        .calculateEndPadding(layoutDirection),
                )),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = createWordState.input,
                onValueChange = { value -> onInputChanged(value) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                label = {
                    Text(text = stringResource(R.string.create_word_placeholder))
                },
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        AnimatedContent(
                            targetState = createWordState.error,
                            transitionSpec = {
                                if (targetState != null && initialState == null) {
                                    slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                                } else {
                                    slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                                }.using(SizeTransform(clip = false))
                            },
                        ) { target ->
                            when (target) {
                                CreateWordError.EMPTY -> Text(stringResource(R.string.create_word_error_empty))
                                CreateWordError.SHORT -> Text(stringResource(R.string.create_word_error_short))
                                CreateWordError.NOT_SINGLE -> Text(stringResource(R.string.create_word_error_not_single))
                                null -> Text(stringResource(R.string.create_word_support))
                            }
                        }
                        AnimatedContent(
                            targetState = createWordState.input.length,
                            transitionSpec = {
                                if (targetState > initialState) {
                                    slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                                } else {
                                    slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                                }.using(SizeTransform(clip = false))
                            },
                        ) { targetCount ->
                            Text(text = "$targetCount")
                        }
                    }
                },
                trailingIcon = {
                    Crossfade(targetState = createWordState.input.isNotEmpty()) { value ->
                        if (value) {
                            IconButton(onClick = { onInputChanged(String()) }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    }
                },
                isError = createWordState.error != null,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, autoCorrect = false, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onCreateClicked() }),
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
            )
            FilledTonalButton(
                onClick = {
                    onCreateClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = maxOf(contentPadding.calculateBottomPadding(),
                            WindowInsets.ime
                                .asPaddingValues()
                                .calculateBottomPadding()),
                        start = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                        end = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateEndPadding(layoutDirection),
                    ),
            ) {
                Text(text = stringResource(R.string.submit))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCreateWordScreen() {
    CreateWordScreen(
        createWordState = CreateWordState(),
        onInputChanged = {},
        onCreateClicked = {},
    )
}
