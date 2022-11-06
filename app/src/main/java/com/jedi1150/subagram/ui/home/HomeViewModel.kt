package com.jedi1150.subagram.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jedi1150.subagram.data.Repository
import com.jedi1150.subagram.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    var navigateToWord: ((Word) -> Unit)? = null

    init {
        Log.d("Subagram", "HomeViewModel:: init")
        uiState = uiState.copy(words = repository.getWordsWithAnagrams())
    }

    fun addWord(value: String) {
        viewModelScope.launch {
            repository.addWord(value).let { uid ->
                repository.getWord(uid)?.let { word ->
                    navigateToWord?.invoke(word.word)
                }
            }
        }
    }

}
