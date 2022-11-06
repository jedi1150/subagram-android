package com.jedi1150.subagram.ui.word

import com.jedi1150.subagram.data.Anagram
import com.jedi1150.subagram.data.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WordUiState(
    var currentWord: Word = Word(String()),
    val anagrams: Flow<List<Anagram>> = emptyFlow(),
    val input: String = String(),
    val isError: Boolean = false,
)
