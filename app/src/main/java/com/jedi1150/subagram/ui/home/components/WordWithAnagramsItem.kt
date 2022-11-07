package com.jedi1150.subagram.ui.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jedi1150.subagram.R
import com.jedi1150.subagram.data.Word
import com.jedi1150.subagram.data.WordWithAnagrams

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WordWithAnagramsItem(
    wordWithAnagrams: WordWithAnagrams,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
) {
    ListItem(
        headlineText = {
            Text(text = wordWithAnagrams.word.value)
        },
        modifier = modifier.combinedClickable(onClick = onClick, onLongClick = onLongClick),
        supportingText = {
            Text(text = stringResource(R.string.anagrams_count, wordWithAnagrams.anagrams.size))
        },
    )
}

@Preview
@Composable
private fun PreviewWordItem() {
    WordWithAnagramsItem(
        wordWithAnagrams = WordWithAnagrams(Word("Word"), emptyList()),
        onLongClick = {},
        onClick = {},
    )
}
