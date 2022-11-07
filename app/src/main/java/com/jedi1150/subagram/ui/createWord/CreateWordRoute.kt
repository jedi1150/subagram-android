package com.jedi1150.subagram.ui.createWord

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.jedi1150.subagram.data.Word
import com.jedi1150.subagram.ui.home.HomeViewModel

@Composable
fun CreateWordRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(),
    navigateToWord: (Word) -> Unit,
) {
    viewModel.navigateToWord = navigateToWord::invoke

    CreateWordScreen(
        createWordState = viewModel.createWordState,
        contentPadding = contentPadding,
        onInputChanged = viewModel::changeInput,
        onCreateClicked = viewModel::addWord,
    )
}
