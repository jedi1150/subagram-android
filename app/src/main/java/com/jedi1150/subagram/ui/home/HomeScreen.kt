package com.jedi1150.subagram.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jedi1150.subagram.R
import com.jedi1150.subagram.data.Word
import com.jedi1150.subagram.ui.home.components.WordWithAnagramsItem
import com.jedi1150.subagram.ui.theme.Typography

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    contentPadding: PaddingValues = PaddingValues(),
    onCreateWordClicked: () -> Unit,
    onDeleteWordClicked: (Word) -> Unit,
    onWordClick: (Word) -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current

    val wordsWithAnagrams by uiState.words.collectAsState(initial = null)
    val lazyListState = rememberLazyListState()

    var showDeleteWordDialog by remember { mutableStateOf<Word?>(null) }

    showDeleteWordDialog?.let { word ->
        AlertDialog(
            onDismissRequest = { showDeleteWordDialog = null },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteWordClicked(word)
                    showDeleteWordDialog = null
                }) {
                    Text(text = stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteWordDialog = null }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            },
            title = {
                Text(text = word.value)
            },
            text = {
                Text(text = stringResource(R.string.delete_word_description))
            },
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = wordsWithAnagrams,
            modifier = Modifier.fillMaxSize(),
            transitionSpec = {
                fadeIn() with fadeOut()
            },
            contentAlignment = Alignment.Center,
        ) { value ->
            if (value != null) {
                if (value.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = contentPadding.calculateTopPadding()),
                        state = lazyListState,
                        contentPadding = PaddingValues(
                            start = WindowInsets.navigationBars.asPaddingValues().calculateStartPadding(layoutDirection) + WindowInsets.displayCutout.asPaddingValues().calculateStartPadding(layoutDirection),
                            end = WindowInsets.navigationBars.asPaddingValues().calculateEndPadding(layoutDirection) + WindowInsets.displayCutout.asPaddingValues().calculateEndPadding(layoutDirection),
                            bottom = contentPadding.calculateBottomPadding(),
                        ),
                    ) {
                        items(value, key = { wordWithAnagrams -> wordWithAnagrams.word.uid }) { wordWithAnagrams ->
                            WordWithAnagramsItem(
                                wordWithAnagrams,
                                modifier = Modifier.animateItemPlacement(),
                                onLongClick = {
                                    showDeleteWordDialog = wordWithAnagrams.word
                                },
                                onClick = { onWordClick(wordWithAnagrams.word) },
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = stringResource(R.string.no_words_title),
                                modifier = Modifier.padding(8.dp),
                                style = Typography.headlineLarge,
                            )
                            FilledTonalButton(onClick = onCreateWordClicked) {
                                Text(text = stringResource(R.string.no_words_description))
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    HomeScreen(
        onCreateWordClicked = {},
        onDeleteWordClicked = {},
        onWordClick = {},
    )
}
