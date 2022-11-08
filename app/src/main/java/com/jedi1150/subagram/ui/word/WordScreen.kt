package com.jedi1150.subagram.ui.word

import androidx.compose.animation.*
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.jedi1150.subagram.R
import com.jedi1150.subagram.data.Anagram
import com.jedi1150.subagram.ui.theme.Typography
import com.jedi1150.subagram.ui.word.components.AnagramHeader
import com.jedi1150.subagram.ui.word.components.AnagramItem
import com.jedi1150.subagram.ui.word.components.InputPanel
import kotlinx.coroutines.cancel

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun WordScreen(
    uiState: WordUiState = WordUiState(),
    contentPadding: PaddingValues = PaddingValues(),
    onInputChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit,
    onDeleteAnagramClicked: (Anagram) -> Unit,
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val layoutDirection = LocalLayoutDirection.current

    val anagrams by remember(uiState.anagrams, lifecycleOwner) { uiState.anagrams.flowWithLifecycle(lifecycleOwner.lifecycle) }.collectAsState(initial = emptyList())
    var isLoaded by remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()
    val lastAnagramIsVisible by remember(anagrams.size) {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.isEmpty() || lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
    }

    var showDeleteAnagramDialog by remember { mutableStateOf<Anagram?>(null) }

    LaunchedEffect(uiState.anagrams) {
        uiState.anagrams.collect {
            isLoaded = true
            cancel()
        }
    }

    LaunchedEffect(anagrams.size) {
        if (lastAnagramIsVisible) {
            lazyListState.scrollToItem(anagrams.size)
        }
    }

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = contentPadding.calculateTopPadding()),
            ) {
                AnimatedContent(
                    targetState = isLoaded,
                    modifier = Modifier.fillMaxSize(),
                    transitionSpec = { fadeIn() with fadeOut() },
                    contentAlignment = Alignment.Center,
                ) { isLoaded ->
                    if (isLoaded) {
                        AnimatedContent(
                            targetState = anagrams.isNotEmpty(),
                            modifier = Modifier.fillMaxSize(),
                            transitionSpec = { fadeIn() with fadeOut() },
                            contentAlignment = Alignment.Center,
                        ) { notEmpty ->
                            if (notEmpty) {
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
                    } else {
                        Box(modifier = Modifier.fillMaxSize())
                    }
                }
            }

            InputPanel(
                uiState = uiState,
                onInputChanged = onInputChanged,
                onSubmitClicked = {
                    onSubmitClicked()
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
