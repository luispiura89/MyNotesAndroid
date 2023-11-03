package com.example.mynotes.myposts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.database.LocalPost
import com.example.mynotes.database.LocalPostChangeLog
import com.example.mynotes.database.LocalPostsDao
import com.example.mynotes.myposts.composables.FilterBy
import com.example.mynotes.myposts.composables.PostListAction
import com.example.mynotes.myposts.composables.PostsListState
import com.example.mynotes.myposts.coroutines.MyAsyncClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class PostsViewModel(
    private val dao: LocalPostsDao
): ViewModel() {

    private val myObject = MyAsyncClass()
    private val _filterByComplete = MutableStateFlow(FilterBy.ALL)
    private var _uiState = MutableStateFlow(PostsListState())
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _localPosts = _filterByComplete.flatMapLatest { filter ->
        when(filter) {
            FilterBy.COMPLETE -> {
                dao.getCompletedPosts()
            }
            FilterBy.ALL -> {
                dao.getPosts()
            }
            FilterBy.PENDING -> {
                dao.getPendingPosts()
            }
        }
    }

    /**
     * The `combine` function take `n` number of Flow<Any> and returns a
     * Flow<TheTypeYouWant>
     * The `stateIn` function converts a Flow<AnyType> into a StateFlow<AnyType>
     */
    val uiState = combine(_uiState, _localPosts, _filterByComplete) { state, posts, filter ->
        state.copy(
            posts = posts.map { localPost ->
                Post(
                    id = UUID.fromString(localPost.id),
                    description = localPost.description,
                    isComplete = localPost.isComplete,
                    createdOn = Date(localPost.creationDate)
                )
            },
            onlyCompletePosts = filter == FilterBy.COMPLETE,
            onlyPendingPosts = filter == FilterBy.PENDING,
            allPosts = filter == FilterBy.ALL
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
            is PostListAction.Filter -> {
                _filterByComplete.update {
                    _filterByComplete.value = action.filteredBy
                    it
                }
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
            val changeLogCount = dao.getPostChangeLog(post.id.toString()).size
            dao.upsertLog(
                LocalPostChangeLog(
                    changeType = if (changeLogCount == 0) "created" else "updated",
                    postId = post.id.toString()
                )
            )
        }
    }
}