package com.example.mynotes.api

import com.example.mynotes.database.LocalPost
import com.example.mynotes.database.LocalPostChangeLog
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun fetchPosts(): List<RemotePost>

    fun getAllLocalPosts(): Flow<List<LocalPost>>

    fun getCompletedLocalPosts(): Flow<List<LocalPost>>

    fun getPendingLocalPosts(): Flow<List<LocalPost>>

    suspend fun getPostChangeLog(postId: String): List<LocalPostChangeLog>

    suspend fun addLocalPost(post: LocalPost)


    suspend fun addLocalPostLog(log: LocalPostChangeLog)

    suspend fun deleteLocalPost(post: LocalPost)
}