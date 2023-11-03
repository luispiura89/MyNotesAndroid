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

    @Upsert
    suspend fun upsertLog(log: LocalPostChangeLog)

    @Delete
    suspend fun delete(post: LocalPost)

    @Query("SELECT * from localpost order by creationDate desc")
    fun getPosts(): Flow<List<LocalPost>>

    @Query("SELECT * from localpost where isComplete = 1 order by creationDate desc")
    fun getCompletedPosts(): Flow<List<LocalPost>>

    @Query("SELECT * from localpost where isComplete = 0 order by creationDate desc")
    fun getPendingPosts(): Flow<List<LocalPost>>

    @Query("SELECT * from localpostchangelog where postId = :postId")
    suspend fun getPostChangeLog(postId: String): List<LocalPostChangeLog>
}