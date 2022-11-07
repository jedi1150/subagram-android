package com.jedi1150.subagram.ui.word.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jedi1150.subagram.R
import com.jedi1150.subagram.data.Anagram

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AnagramItem(
    anagram: Anagram,
    modifier: Modifier = Modifier,
    index: Int = 0,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
) {
    ListItem(
        headlineText = {
            Text(text = anagram.value)
        },
        modifier = modifier.combinedClickable(onLongClick = onLongClick, onClick = onClick),
        leadingContent = {
            Text(text = stringResource(R.string.index_position, index))
        },
    )
}

@Preview
@Composable
private fun PreviewAnagram() {
    AnagramItem(
        anagram = Anagram("Anagram", 0L),
        onLongClick = {},
        onClick = {},
    )
}
