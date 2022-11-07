package com.jedi1150.subagram.ui.word

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.jedi1150.subagram.data.Anagram
import com.jedi1150.subagram.ui.theme.Typography
import com.jedi1150.subagram.ui.word.components.AnagramHeader
import com.jedi1150.subagram.ui.word.components.AnagramItem
import com.jedi1150.subagram.ui.word.components.InputPanel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun WordScreen(
    uiState: WordUiState = WordUiState(),
    contentPadding: PaddingValues = PaddingValues(),
    onInputChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit,
    onDeleteAnagramClicked: (Anagram) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val layoutDirection = LocalLayoutDirection.current

    val anagrams by uiState.anagrams.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()
    val lastAnagramIsVisible by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.isEmpty() || lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
    }

    var showDeleteAnagramDialog by remember { mutableStateOf<Anagram?>(null) }

    showDeleteAnagramDialog?.let { word ->
        AlertDialog(
            onDismissRequest = { showDeleteAnagramDialog = null },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteAnagramClicked(word)
                    showDeleteAnagramDialog = null
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAnagramDialog = null }) {
                    Text(text = stringResource(id = R.string.delete))
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            },
            title = {
                Text(text = word.value)
            },
            text = {
                Text(text = stringResource(R.string.delete_anagram_description))
            },
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedContent(
                targetState = anagrams.isNotEmpty(),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = contentPadding.calculateTopPadding()),
                transitionSpec = {
                    fadeIn(animationSpec = tween()) with fadeOut(animationSpec = tween())
                },
                contentAlignment = Alignment.Center,
            ) { value ->
                if (value) {
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(
                            start = WindowInsets.navigationBars.asPaddingValues().calculateStartPadding(layoutDirection) + WindowInsets.displayCutout.asPaddingValues().calculateStartPadding(layoutDirection),
                            end = WindowInsets.navigationBars.asPaddingValues().calculateEndPadding(layoutDirection) + WindowInsets.displayCutout.asPaddingValues().calculateEndPadding(layoutDirection),
                        ),
                    ) {
                        stickyHeader {
                            AnagramHeader(
                                anagrams.size,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            )
                        }
                        itemsIndexed(anagrams, key = { _, anagram -> anagram.uid }) { index, anagram ->
                            AnagramItem(
                                anagram,
                                modifier = Modifier.animateItemPlacement(),
                                index = index.plus(1),
                                onLongClick = { showDeleteAnagramDialog = anagram },
                                onClick = {},
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = stringResource(R.string.no_anagrams_title),
                                style = Typography.headlineLarge,
                            )
                            Text(
                                text = stringResource(R.string.no_anagrams_description),
                                style = Typography.bodyLarge,
                            )
                        }
                    }
                }
            }

            InputPanel(
                uiState = uiState,
                onInputChanged = onInputChanged,
                onSubmitClicked = {
                    onSubmitClicked()
                    if (lastAnagramIsVisible) {
                        scope.launch {
                            lazyListState.animateScrollToItem(anagrams.size)
                        }
                    }
                },
                contentPadding = contentPadding,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWordScreen() {
    WordScreen(
        onInputChanged = {},
        onSubmitClicked = {},
        onDeleteAnagramClicked = {},
    )
}
