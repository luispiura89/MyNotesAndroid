package com.example.mynotes.myposts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.database.LocalPost
import com.example.mynotes.database.LocalPostsDao
import com.example.mynotes.myposts.composables.PostListAction
import com.example.mynotes.myposts.composables.PostsListState
import com.example.mynotes.myposts.coroutines.MyAsyncClass
import com.example.mynotes.navigation.PostScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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

    /**
     * The `combine` function take `n` number of Flow<Any> and returns a
     * Flow<TheTypeYouWant>
     * The `stateIn` function converts a Flow<AnyType> into a StateFlow<AnyType>
     */
    val uiState = combine(_uiState, _localPosts) { state, posts ->
        state.copy(
            posts = posts.map { localPost ->
                Post(
                    id = UUID.fromString(localPost.id),
                    description = localPost.description,
                    isComplete = localPost.isComplete,
                    createdOn = Date(localPost.creationDate)
                )
            }
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PostsListState()
        )

    fun handle(action: PostListAction) {
        when(action) {
            is PostListAction.Remove -> {
                remove(action.post)
            }
            is PostListAction.MarkAsComplete -> {
                markPostAsComplete(action.post)
            }
            is PostListAction.MarkAsIncomplete -> {
                markPostAsIncomplete(action.post)
            }
            is PostListAction.Edit -> {
                update(action.post)
            }
            is PostListAction.Add -> {
                action.post?.let {
                    add(it)
                }
            }
            is PostListAction.FetchPosts -> {
                fetchPosts()
            }
            else -> {}
        }
    }
    private fun add(post: Post) {
        upsert(post)
    }

    private fun add(posts: List<Post>) {
        posts.forEach {
            add(it)
        }
    }

    private fun remove(post: Post) {
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

    private fun markPostAsComplete(post: Post) {
        update(post.copy(isComplete = true))
    }
    private fun markPostAsIncomplete(post: Post) {
        update(post.copy(isComplete = false))
    }

    private fun update(post: Post) {
        upsert(post)
    }

    private fun fetchPosts() {
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