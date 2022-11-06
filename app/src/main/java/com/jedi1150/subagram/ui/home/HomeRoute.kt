package com.jedi1150.subagram.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.jedi1150.subagram.data.Word

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    navigateToCreateWord: () -> Unit,
    navigateToWord: (Word) -> Unit,
) {
    HomeScreen(
        uiState = viewModel.uiState,
        contentPadding = contentPadding,
        onCreateWordClicked = navigateToCreateWord,
        onWordClicked = navigateToWord
    )
}
