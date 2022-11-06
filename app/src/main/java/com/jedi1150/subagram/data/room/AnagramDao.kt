package com.jedi1150.subagram.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jedi1150.subagram.data.Anagram
import kotlinx.coroutines.flow.Flow

@Dao
interface AnagramDao {
    @Query("SELECT * FROM anagrams WHERE word_uid == :wordUID")
    fun getByWord(wordUID: Long): Flow<List<Anagram>>

    @Query("SELECT * FROM anagrams")
    fun getAll(): List<Anagram>

    @Insert
    fun insert(anagram: Anagram)

    @Delete
    fun delete(anagram: Anagram)
}
