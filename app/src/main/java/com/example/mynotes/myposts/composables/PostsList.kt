package com.example.mynotes.myposts.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.myposts.MainLayout
import com.example.mynotes.myposts.Post
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun PostsList(
    state: PostsListState,
    onAction: (PostListAction) -> Unit
) {
    LazyColumn {
        items(state.posts) {
            PostCard(
                post = it,
                onAction = onAction
            )
        }
    }
}

sealed class PostListAction {
    object Add : PostListAction()
    data class Select(val post: Post): PostListAction()
    data class Edit(val post: Post): PostListAction()
    data class Remove(val post: Post): PostListAction()
    data class MarkAsComplete(val post: Post): PostListAction()
    data class MarkAsIncomplete(val post: Post): PostListAction()
}

data class PostsListState(
    val posts: List<Post> = emptyList()
)

@Preview(showBackground = true)
@Composable
fun PostPreview() {
    MyNotesTheme {
        MainLayout(
            state = PostsListState(
                posts = listOf(
                    Post(
                        description = "This is my first post description. I don't have anything to say."
                    )
                )
            ),
            onFetchPosts = {}
        ) { _ ->

        }
    }
}