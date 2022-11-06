package com.jedi1150.subagram.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anagrams")
data class Anagram(
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "word_uid") val wordUID: Long,
) {
    @PrimaryKey(autoGenerate = true) var uid: Long = 0L
}
