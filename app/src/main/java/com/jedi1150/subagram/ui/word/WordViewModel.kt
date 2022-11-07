package com.jedi1150.subagram.ui.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jedi1150.subagram.data.Anagram
import com.jedi1150.subagram.data.Repository
import com.jedi1150.subagram.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {
    private val uid: Long = checkNotNull(savedStateHandle["uid"])

    var uiState by mutableStateOf(WordUiState())
        private set

    init {
        viewModelScope.launch {
            repository.getWord(uid)?.let { word ->
                uiState = uiState.copy(
                    currentWord = word,
                    anagrams = repository.getAnagramsByWord(word),
                )
            }
        }
    }

    fun changeInput(value: String) {
        uiState = uiState.copy(input = value, error = null)
    }

    fun addAnagram() {
        viewModelScope.launch {
            if (uiState.input.isEmpty()) {
                uiState = uiState.copy(error = AnagramError.EMPTY)
                return@launch
            }
            if (uiState.input.length < 2) {
                uiState = uiState.copy(error = AnagramError.SHORT)
                return@launch
            }
            if (uiState.input.contains(" ")) {
                uiState = uiState.copy(error = AnagramError.NOT_SINGLE)
                return@launch
            }
            if (uiState.input == uiState.currentWord.value) {
                uiState = uiState.copy(error = AnagramError.SAME)
                return@launch
            }
            if (!isAnagram(uiState.input, uiState.currentWord)) {
                uiState = uiState.copy(error = AnagramError.NOT_ANAGRAM)
                return@launch
            }
            if (anagramExists(uiState.input, uiState.anagrams.first())) {
                uiState = uiState.copy(error = AnagramError.ALREADY_EXISTS)
                return@launch
            }

            uiState.currentWord.let { word -> repository.addAnagram(uiState.input, word) }
            uiState = uiState.copy(input = String())
        }
    }

    fun deleteAnagram(anagram: Anagram) {
        repository.deleteAnagram(anagram)
    }

    private fun isAnagram(input: String, word: Word): Boolean {
        val wordSorted = word.value.lowercase().toCharArray().sortedArray()
        val inputSorted = input.lowercase().toCharArray().sortedArray()

        var wordIndex = 0
        var inputIndex = 0

        while (wordIndex < wordSorted.size) {
            if (inputSorted[inputIndex] == wordSorted[wordIndex]) {
                inputIndex += 1
                if (inputIndex >= inputSorted.size) {
                    return true
                }
            }
            wordIndex += 1
        }

        return false
    }

    private fun anagramExists(input: String, anagrams: List<Anagram>): Boolean {
        return anagrams.any { anagram ->
            anagram.value == input
        }
    }

}


