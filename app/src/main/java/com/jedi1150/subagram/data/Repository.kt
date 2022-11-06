package com.jedi1150.subagram.data

import android.content.Context
import androidx.room.Room
import com.jedi1150.subagram.data.room.SubagramDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class Repository @Inject constructor(@ApplicationContext context: Context) : CoroutineScope {
    @OptIn(DelicateCoroutinesApi::class)
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Default + newSingleThreadContext("repository")

    private val db = Room.databaseBuilder(
        context,
        SubagramDatabase::class.java, "database-subagram"
    ).build()

    suspend fun addWord(value: String): Long {
        return withContext(coroutineContext) {
            db.wordDao().insert(Word(value))
        }
    }

    suspend fun getWord(uid: Long): WordWithAnagrams? {
        return withContext(coroutineContext) { db.wordDao().getWordWithAnagrams(uid) }
    }

    fun getWords(): Flow<List<Word>> {
        return db.wordDao().getAll()
    }

    fun getWordsWithAnagrams(): Flow<List<WordWithAnagrams>> {
        return db.wordDao().getWordsWithAnagrams()
    }

    fun deleteWord(word: Word) {
        launch {
            db.wordDao().delete(word)
        }
    }

    fun addAnagram(value: String, word: Word) {
        launch {
            db.anagramDao().insert(Anagram(value, word.uid))
        }
    }

    fun getAnagramsByWord(word: Word): Flow<List<Anagram>> {
        return db.anagramDao().getByWord(word.uid)
    }

    fun deleteAnagram(anagram: Anagram) {
        launch {
            db.anagramDao().delete(anagram)
        }
    }

}
