package com.example.mynotes.myposts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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

@Composable
fun MainLayout(
    modifier: Modifier = Modifier,
    state: PostsListState,
    onAddPost: () -> Unit,
    onSelectPost: (Post) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(onClick = onAddPost) {
            Text(text = "Add")
        }
        Spacer(modifier = modifier.height(15.dp))
        if (state.posts.isEmpty()) {
            EmptyPostsPlaceholder()
        } else {
            PostsList(
                state = state,
                onSelectPost = onSelectPost
            )
        }
    }
}

@Composable
fun PostsList(state: PostsListState, onSelectPost: (Post) -> Unit) {
    LazyColumn {
        items(state.posts) {
            PostCard(post = it, onSelectPost = onSelectPost)
        }
    }
}

@Composable
fun PostCard(post: Post, modifier: Modifier = Modifier, onSelectPost: (Post) -> Unit) {
    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                           onSelectPost(post)
                },
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column {
                Text(
                    text = post.title,
                    modifier = modifier.padding(20.dp)
                )
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
                        title = "My post",
                        description = "This is my first post description. I don't have anything to say."
                    )
                )
            ),
            onAddPost = {}
        ) {

        }
    }
}