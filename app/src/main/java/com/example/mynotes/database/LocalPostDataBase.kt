package com.example.mynotes.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalPost::class, LocalPostChangeLog::class],
    version = 1
)
abstract class LocalPostDataBase: RoomDatabase() {
    abstract val localPostsDao: LocalPostsDao
}