package com.jedi1150.subagram.ui.word

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val anagrams by uiState.anagrams.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()
    val lastAnagramIsVisible by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.isEmpty() || lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
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
                    LazyColumn(state = lazyListState) {
                        stickyHeader {
                            AnagramHeader(
                                anagrams.size,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            )
                        }
                        items(anagrams, key = { anagram -> anagram.uid }) { anagram ->
                            AnagramItem(
                                anagram,
                                modifier = Modifier.animateItemPlacement(),
                                onDeleteClicked = { onDeleteAnagramClicked.invoke(anagram) },
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
                                text = "No anagrams yet",
                                style = Typography.headlineLarge,
                            )
                            Text(
                                text = "Start writing words",
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
