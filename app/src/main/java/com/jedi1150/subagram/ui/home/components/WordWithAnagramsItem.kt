package com.jedi1150.subagram.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jedi1150.subagram.data.Word
import com.jedi1150.subagram.data.WordWithAnagrams

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordWithAnagramsItem(
    wordWithAnagrams: WordWithAnagrams,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
) {
    ListItem(
        headlineText = {
            Text(text = wordWithAnagrams.word.value)
        },
        modifier = modifier.clickable(onClick = onClicked::invoke),
        supportingText = {
            Text(text = "Anagrams: ${wordWithAnagrams.anagrams.size}")
        },
    )
}

@Preview
@Composable
private fun PreviewWordItem() {
    WordWithAnagramsItem(
        wordWithAnagrams = WordWithAnagrams(Word("Word"), emptyList()),
    ) {}
}
