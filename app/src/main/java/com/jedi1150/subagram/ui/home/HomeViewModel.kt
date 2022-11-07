package com.jedi1150.subagram.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jedi1150.subagram.data.Repository
import com.jedi1150.subagram.data.Word
import com.jedi1150.subagram.ui.createWord.CreateWordError
import com.jedi1150.subagram.ui.createWord.CreateWordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set
    var createWordState by mutableStateOf(CreateWordState())
        private set

    var navigateToWord: ((Word) -> Unit)? = null

    init {
        viewModelScope.launch {
            uiState = uiState.copy(words = repository.getWordsWithAnagrams())
        }
    }

    fun changeInput(value: String) {
        createWordState = createWordState.copy(input = value, error = null)
    }

    fun addWord() {
        viewModelScope.launch {
            if (createWordState.input.isEmpty()) {
                createWordState = createWordState.copy(error = CreateWordError.EMPTY)
                return@launch
            }
            if (createWordState.input.length < 3) {
                createWordState = createWordState.copy(error = CreateWordError.SHORT)
                return@launch
            }
            if (createWordState.input.contains(" ")) {
                createWordState = createWordState.copy(error = CreateWordError.NOT_SINGLE)
                return@launch
            }

            checkWordExists(createWordState.input).let { word ->
                if (word != null) {
                    navigateToWord?.invoke(word)
                } else {
                    repository.addWord(createWordState.input).let { uid ->
                        repository.getWord(uid)?.let { word ->
                            navigateToWord?.invoke(word)
                        }
                    }
                }
            }
        }
    }

    fun deleteWord(word: Word) {
        repository.deleteWord(word)
    }

    private suspend fun checkWordExists(value: String): Word? {
        return withContext(viewModelScope.coroutineContext) { repository.getWord(value) }
    }

}
