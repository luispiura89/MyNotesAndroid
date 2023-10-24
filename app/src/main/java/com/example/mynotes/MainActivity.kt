package com.example.mynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.myposts.Post
import com.example.mynotes.myposts.PostsFlow
import com.example.mynotes.myposts.PostsList
import com.example.mynotes.myposts.PostsListState
import com.example.mynotes.ui.theme.MyNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                PostsFlow()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme {
        PostsList(
            PostsListState(
                posts = listOf(
                    Post("first post", description = "This is my first post"),
                    Post("second post", description = "This is my second post"),
                )
            )
        ) {

        }
    }
}