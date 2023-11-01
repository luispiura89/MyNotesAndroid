package com.example.mynotes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalPostsDao {
    @Upsert
    suspend fun upsert(post: LocalPost)

    @Delete
    suspend fun delete(post: LocalPost)

    @Query("SELECT * from localpost order by creationDate desc")
    fun getPosts(): Flow<List<LocalPost>>
}