package com.example.mynotes.myposts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.api.PostsRepository
import com.example.mynotes.api.RemotePost
import com.example.mynotes.database.LocalPost
import com.example.mynotes.database.LocalPostChangeLog
import com.example.mynotes.myposts.composables.FilterBy
import com.example.mynotes.myposts.composables.PostListAction
import com.example.mynotes.myposts.composables.PostsListState
import com.example.mynotes.myposts.coroutines.MyAsyncClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostsRepository
): ViewModel() {

    private val myObject = MyAsyncClass()
    private val _filterByComplete = MutableStateFlow(FilterBy.ALL)
    private var _uiState = MutableStateFlow(PostsListState())
    private val _remotePosts = MutableStateFlow(listOf<RemotePost>())
    private val _filteredRemotePosts = combine(_filterByComplete, _remotePosts) { filter, posts ->
        when(filter) {
            FilterBy.COMPLETE -> {
                posts.filter {
                    it.completed
                }
            }
            FilterBy.ALL -> {
                posts
            }
            FilterBy.PENDING -> {
                posts.filter {
                    !it.completed
                }
            }
        }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _localPosts = _filterByComplete.flatMapLatest { filter ->
        when(filter) {
            FilterBy.COMPLETE -> {
                repository.getCompletedLocalPosts()
            }
            FilterBy.ALL -> {
                repository.getAllLocalPosts()
            }
            FilterBy.PENDING -> {
                repository.getPendingLocalPosts()
            }
        }
    }

    /**
     * The `combine` function take `n` number of Flow<Any> and returns a
     * Flow<TheTypeYouWant>
     * The `stateIn` function converts a Flow<AnyType> into a StateFlow<AnyType>
     */
    val uiState = combine(
        _uiState, _localPosts, _filteredRemotePosts, _filterByComplete
    ) { state, localPosts, remotePosts, filter ->
        state.copy(
            posts = localPosts.map { localPost ->
                Post(
                    id = UUID.fromString(localPost.id),
                    description = localPost.description,
                    isComplete = localPost.isComplete,
                    createdOn = Date(localPost.creationDate)
                )
            }
                .toMutableList()
                .also {
                    it.addAll(remotePosts.map { remotePost ->
                        Post(
                            description = remotePost.title,
                            isComplete = remotePost.completed,
                            createdOn = Date()
                        )
                    })
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
            repository.deleteLocalPost(
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
//        viewModelScope.launch(Dispatchers.IO) {
//            val posts1 = async { myObject.fetchPostsFirstPage() }
//            val posts2 = async { myObject.fetchPostsSecondPage() }
//            add(posts1.await())
//            add(posts2.await())
//        }
        viewModelScope.launch {
            val posts = repository.fetchPosts()
            _remotePosts.update {
                _remotePosts.value = posts
                it
            }

        }
    }

    private fun upsert(post: Post) {
        viewModelScope.launch {
            repository.addLocalPost(
                LocalPost(
                    id = post.id.toString(),
                    description = post.description,
                    isComplete = post.isComplete,
                    creationDate = post.createdOn.time
                )
            )
            val changeLogCount = repository.getPostChangeLog(post.id.toString()).size
            repository.addLocalPostLog(
                LocalPostChangeLog(
                    changeType = if (changeLogCount == 0) "created" else "updated",
                    postId = post.id.toString()
                )
            )
        }
    }
}