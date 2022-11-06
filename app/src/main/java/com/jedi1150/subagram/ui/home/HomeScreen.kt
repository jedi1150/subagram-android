package com.jedi1150.subagram.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jedi1150.subagram.data.Word
import com.jedi1150.subagram.ui.home.components.WordWithAnagramsItem
import com.jedi1150.subagram.ui.theme.Typography

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    contentPadding: PaddingValues = PaddingValues(),
    onCreateWordClicked: () -> Unit,
    onWordClicked: (Word) -> Unit,
) {
    val wordsWithAnagrams by uiState.words.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedContent(
            targetState = wordsWithAnagrams.isNotEmpty(),
            modifier = Modifier.fillMaxSize(),
            transitionSpec = {
                fadeIn(animationSpec = tween()) with fadeOut(animationSpec = tween())
            },
            contentAlignment = Alignment.Center,
        ) { value ->
            if (value) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                ) {
                    items(wordsWithAnagrams, key = { wordWithAnagrams -> wordWithAnagrams.word.uid }) { wordWithAnagrams ->
                        WordWithAnagramsItem(
                            wordWithAnagrams,
                            modifier = Modifier.animateItemPlacement(),
                        ) {
                            onWordClicked(wordWithAnagrams.word)
                        }
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
                            text = "No words yet",
                            modifier = Modifier.padding(8.dp),
                            style = Typography.headlineLarge,
                        )
                        FilledTonalButton(onClick = onCreateWordClicked) {
                            Text(text = "Add new word")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    HomeScreen(
        onCreateWordClicked = {},
        onWordClicked = {},
    )
}
