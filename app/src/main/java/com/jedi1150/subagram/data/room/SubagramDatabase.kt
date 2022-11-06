package com.jedi1150.subagram.data.room

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.jedi1150.subagram.data.Anagram
import com.jedi1150.subagram.data.Word

@Database(
    entities = [
        Word::class,
        Anagram::class,
    ],
    version = 1,
)
abstract class SubagramDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun anagramDao(): AnagramDao
}
