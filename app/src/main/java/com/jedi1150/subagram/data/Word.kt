package com.jedi1150.subagram.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @ColumnInfo(name = "value") val value: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L
}
