package com.jedi1150.subagram.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "words")
data class WordWithAnagrams(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "uid",
        entityColumn = "word_uid"
    )
    val anagrams: List<Anagram>
)
