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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.myposts.composables.EmptyPostsPlaceholder
import com.example.mynotes.myposts.composables.FilterBy
import com.example.mynotes.myposts.composables.PostListAction
import com.example.mynotes.myposts.composables.PostsList
import com.example.mynotes.myposts.composables.PostsListState
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun MainLayout(
    modifier: Modifier = Modifier,
    state: PostsListState,
    onFetchPosts: () -> Unit,
    onAction: (PostListAction) -> Unit
) {
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
                onFetchPosts()
            }) {
                Text(text = "Fetch posts")
            }
            Button(onClick = {
                onAction(PostListAction.Add())
            }) {
                Text(text = "Add")
            }
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.onlyCompletePosts,
                    onClick = { onAction(PostListAction.Filter(filteredBy = FilterBy.COMPLETE)) }
                )
                Text(text = "Completed")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.allPosts,
                    onClick = { onAction(PostListAction.Filter(filteredBy = FilterBy.ALL)) }
                )
                Text(text = "All")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.onlyPendingPosts,
                    onClick = { onAction(PostListAction.Filter(filteredBy = FilterBy.PENDING)) }
                )
                Text(text = "Pending")
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
                    Post(description = "This is my first post"),
                    Post(description = "This is my second post"),
                )
            ),
            onFetchPosts = {},
            onAction = { _ ->

            }
        )
    }
}