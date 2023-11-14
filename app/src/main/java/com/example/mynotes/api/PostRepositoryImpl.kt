package com.example.mynotes.api

import com.example.mynotes.database.LocalPost
import com.example.mynotes.database.LocalPostChangeLog
import com.example.mynotes.database.LocalPostsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postsApi: PostsApi,
    private val postsDao: LocalPostsDao
) : PostsRepository {
    override suspend fun fetchPosts(): List<RemotePost> {
        return postsApi.getPosts()
    }

    override fun getAllLocalPosts(): Flow<List<LocalPost>> {
        return postsDao.getPosts()
    }

    override fun getCompletedLocalPosts(): Flow<List<LocalPost>> {
        return postsDao.getCompletedPosts()
    }

    override fun getPendingLocalPosts(): Flow<List<LocalPost>> {
        return postsDao.getPendingPosts()
    }

    override suspend fun getPostChangeLog(postId: String): List<LocalPostChangeLog> {
        return postsDao.getPostChangeLog(postId)
    }

    override suspend fun addLocalPost(post: LocalPost) {
        postsDao.upsert(post)
    }

    override suspend fun addLocalPostLog(log: LocalPostChangeLog) {
        postsDao.upsertLog(log)
    }

    override suspend fun deleteLocalPost(post: LocalPost) {
        postsDao.delete(post)
    }

}