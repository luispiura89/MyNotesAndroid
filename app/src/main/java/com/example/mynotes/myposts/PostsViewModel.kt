package com.example.mynotes.myposts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.myposts.composables.PostsListState
import com.example.mynotes.myposts.coroutines.MyAsyncClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel: ViewModel() {

    private val myObject = MyAsyncClass()
    private var _uiState = MutableStateFlow(PostsListState())
    val uiState = _uiState.asStateFlow()

    fun add(post: Post) {
        _uiState.update { state ->
            state.copy(
                posts = _uiState.value.posts.toMutableList().also {
                    it.add(0, post)
                }
            )
        }
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
        _uiState.update { state ->
            state.copy(
                posts = _uiState.value.posts.toMutableList().also {
                    it.remove(post)
                }
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
        _uiState.update { state ->
            state.copy(
                posts = _uiState.value.posts.toMutableList().also {
                    val position = it.indexOfFirst { postItem ->
                        post.id == postItem.id
                    }
                    if (position >= 0) {
                        it[position] = post
                    }
                }
            )
        }
    }

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val posts1 = async { myObject.fetchPostsFirstPage() }
            val posts2 = async { myObject.fetchPostsSecondPage() }
            add(posts1.await())
            add(posts2.await())
        }
    }
}