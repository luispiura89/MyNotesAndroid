package com.example.mynotes.myposts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.ui.theme.MyNotesTheme
import java.util.UUID

@Composable
fun MainLayout(
    modifier: Modifier = Modifier,
    state: PostsListState,
    onAction: (PostListAction, Post?) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(onClick = {
            onAction(PostListAction.ADD, null)
        }) {
            Text(text = "Add")
        }
        Spacer(modifier = modifier.height(15.dp))
        if (state.posts.isEmpty()) {
            EmptyPostsPlaceholder()
        } else {
            PostsList(
                state = state,
                onAction = onAction
            )
        }
    }
}

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

@Composable
fun PostCard(
    post: Post, modifier: Modifier = Modifier,
    onAction: (PostListAction, Post?) -> Unit
) {
    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onAction(PostListAction.SELECT, post)
                },
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp)
                ) {
                    Text(
                        text = post.title,
                        modifier = modifier
                            .padding(20.dp)
                            .weight(1f)
                    )
                    Row {
                        Button(
                            modifier = modifier.padding(end = 5.dp),
                            onClick = {
                                onAction(PostListAction.EDIT, post)
                            }
                        ) {
                            Text(text = "Edit")
                        }
                        Button(
                            onClick = {
                                onAction(PostListAction.REMOVE, post)
                            }
                        ) {
                            Text(text = "Delete")
                        }
                    }
                }
                Divider(
                    modifier = modifier.background(Color.Gray)
                )
                Text(
                    text = post.description,
                    modifier = modifier.padding(20.dp)
                )
            }
        }
        Spacer(modifier = modifier.height(15.dp))
    }
}

data class PostsListState(
    val posts: List<Post> = emptyList()
)

data class Post(
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = ""
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
        ) { action, post ->

        }
    }
}