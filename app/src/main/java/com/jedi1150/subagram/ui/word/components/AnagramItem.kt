package com.jedi1150.subagram.ui.word.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jedi1150.subagram.data.Anagram

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnagramItem(
    anagram: Anagram,
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit,
) {
    ListItem(
        headlineText = {
            Text(text = anagram.value)
        },
        modifier = modifier,
        trailingContent = {
            IconButton(onClick = onDeleteClicked::invoke) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    )
}

@Preview
@Composable
private fun PreviewAnagram() {
    AnagramItem(
        anagram = Anagram("Anagram", 0L),
        onDeleteClicked = {},
    )
}
