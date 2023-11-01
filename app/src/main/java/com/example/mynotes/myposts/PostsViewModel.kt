package com.example.mynotes.myposts

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.database.LocalPost
import com.example.mynotes.database.LocalPostsDao
import com.example.mynotes.myposts.composables.PostsListState
import com.example.mynotes.myposts.coroutines.MyAsyncClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class PostsViewModel(
    private val dao: LocalPostsDao
): ViewModel() {

    private val myObject = MyAsyncClass()
    private var _uiState = MutableStateFlow(PostsListState())
    private val _localPosts = dao.getPosts()
    val uiState = _localPosts.flatMapMerge { localPosts ->
        _uiState.update {
            it.copy(
                posts = localPosts.map { localPost ->
                    Post(
                        id = UUID.fromString(localPost.id),
                        description = localPost.description,
                        isComplete = localPost.isComplete,
                        createdOn = Date(localPost.creationDate)
                    )
                }
            )
        }
        _uiState.asStateFlow()
    }

    fun add(post: Post) {
        upsert(post)
    }

    private fun add(posts: List<Post>) {
        _uiState.update { state ->
            state.copy(
                posts = _uiState.value.posts.toMutableList().also {
                    it.addAll(0, posts)
                }
            )
        }
    }

    fun remove(post: Post) {
        viewModelScope.launch {
            dao.delete(
                LocalPost(
                    id = post.id.toString(),
                    description = post.description,
                    isComplete = post.isComplete,
                    creationDate = post.createdOn.time
                )
            )
        }
    }

    fun markPostAsComplete(post: Post) {
        update(post.copy(isComplete = true))
    }
    fun markPostAsIncomplete(post: Post) {
        update(post.copy(isComplete = false))
    }

    fun update(post: Post) {
        upsert(post)
    }

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val posts1 = async { myObject.fetchPostsFirstPage() }
            val posts2 = async { myObject.fetchPostsSecondPage() }
            add(posts1.await())
            add(posts2.await())
        }
    }

    private fun upsert(post: Post) {
        viewModelScope.launch {
            dao.upsert(
                LocalPost(
                    id = post.id.toString(),
                    description = post.description,
                    isComplete = post.isComplete,
                    creationDate = post.createdOn.time
                )
            )
        }
    }
}