package com.jedi1150.subagram.ui.home

import com.jedi1150.subagram.data.WordWithAnagrams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val wordsWithAnagrams: Flow<List<WordWithAnagrams>> = emptyFlow(),
)
