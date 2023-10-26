package com.example.mynotes.myposts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.myposts.coroutines.MyAsyncClass
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainLayout(
    modifier: Modifier = Modifier,
    state: PostsListState,
    onFetchPosts: (List<Post>) -> Unit,
    onAction: (PostListAction, Post?) -> Unit
) {
    val scope = rememberCoroutineScope()
    val obj = MyAsyncClass()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    onFetchPosts(obj.fetchPosts())
                }
            }) {
                Text(text = "Fetch posts")
            }
            Button(onClick = {
                onAction(PostListAction.ADD, null)
            }) {
                Text(text = "Add")
            }
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

@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
    MyNotesTheme {
        MainLayout(
            state = PostsListState(
                posts = listOf(
                    Post(title = "first post", description = "This is my first post"),
                    Post(title = "second post", description = "This is my second post"),
                )
            ),
            onFetchPosts = {},
            onAction = { _, _ ->

            }
        )
    }
}