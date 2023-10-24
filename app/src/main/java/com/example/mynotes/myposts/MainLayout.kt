package com.example.mynotes.myposts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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