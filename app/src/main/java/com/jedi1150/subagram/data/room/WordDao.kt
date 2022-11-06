package com.jedi1150.subagram.data.room

import androidx.room.*
import com.jedi1150.subagram.data.Word
import com.jedi1150.subagram.data.WordWithAnagrams
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words WHERE uid = :uid LIMIT 1")
    fun get(uid: Long): Word?

    @Transaction
    @Query("SELECT * FROM words WHERE uid = :uid LIMIT 1")
    fun getWordWithAnagrams(uid: Long): WordWithAnagrams?

    @Transaction
    @Query("SELECT * FROM words")
    fun getWordsWithAnagrams(): Flow<List<WordWithAnagrams>>

    @Query("SELECT * FROM words")
    fun getAll(): Flow<List<Word>>

    @Insert
    fun insert(word: Word): Long

    @Delete
    fun delete(word: Word)
}
