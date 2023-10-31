package com.example.mynotes.myposts.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.myposts.Post

@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier,
    onAction: (PostListAction) -> Unit
) {
    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onAction(PostListAction.Select(post))
                },
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Checkbox(
                        checked = post.isComplete,
                        onCheckedChange = {
                            if (it)
                                onAction(PostListAction.MarkAsComplete(post))
                            else
                                onAction(PostListAction.MarkAsIncomplete(post))
                        }
                    )
                    Row {
                        Button(
                            modifier = modifier.padding(end = 5.dp),
                            onClick = {
                                onAction(PostListAction.Edit(post))
                            }
                        ) {
                            Text(text = "Edit")
                        }
                        Button(
                            onClick = {
                                onAction(PostListAction.Remove(post))
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

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    PostCard(
        post = Post(
        description = "This is my first post description. I don't have anything to say."
        )
    ) {}
}