package com.jedi1150.subagram.ui.word.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    Row(
        modifier = modifier
            .padding(8.dp)
            .padding(
                bottom = maxOf(
                    contentPadding.calculateBottomPadding(),
                    WindowInsets.ime
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
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
                Text(text = "Anagram")
            },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "Minimum 2 letters")
                    AnimatedContent(
                        targetState = uiState.input.length,
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInVertically { height -> height } + fadeIn() with
                                        slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                slideInVertically { height -> -height } + fadeIn() with
                                        slideOutVertically { height -> height } + fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        }
                    ) { targetCount ->
                        Text(text = "$targetCount")
                    }
                }
            },
            isError = uiState.isError,
        )
        FilledTonalButton(
            onClick = {
                onSubmitClicked()
            },
            modifier = Modifier
                .height(TextFieldDefaults.MinHeight)
                .padding(top = 10.dp),
        ) {
            Text(text = "Submit")
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
