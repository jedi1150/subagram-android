package com.jedi1150.subagram.ui.word

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WordRoute(
    viewModel: WordViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(),
) {
    WordScreen(
        uiState = viewModel.uiState,
        onInputChanged = viewModel::changeInput,
        onSubmitClicked = viewModel::addAnagram,
        onDeleteAnagramClicked = viewModel::deleteAnagram,
        contentPadding = contentPadding,
    )
}
