package com.example.mynotes.myposts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun PostsList(
    state: PostsListState,
    onAction: (PostListAction, Post?) -> Unit
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

enum class PostListAction {
    ADD, SELECT, EDIT, REMOVE
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
                        title = "This is my first post description. I don't have anything to say.",
                        description = "This is my first post description. I don't have anything to say."
                    )
                )
            ),
            onFetchPosts = {}
        ) { _, _ ->

        }
    }
}