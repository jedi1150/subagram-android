package com.jedi1150.subagram.ui.createWord

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWordScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onCreateClicked: (String) -> Unit,
) {
    var word by remember { mutableStateOf(String()) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = word,
                onValueChange = { value -> word = value },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            )
            FilledTonalButton(
                onClick = { onCreateClicked(word) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom =
                        maxOf(
                            contentPadding.calculateBottomPadding(),
                            WindowInsets.ime
                                .asPaddingValues()
                                .calculateBottomPadding()
                        )
                    )
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCreateWordScreen() {
    CreateWordScreen(onCreateClicked = {})
}
