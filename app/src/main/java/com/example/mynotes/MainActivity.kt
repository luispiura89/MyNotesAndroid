package com.example.mynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.myposts.MainLayout
import com.example.mynotes.myposts.Post
import com.example.mynotes.navigation.PostsFlow
import com.example.mynotes.myposts.composables.PostsListState
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
        MainLayout(
            state = PostsListState(
                posts = listOf(
                    Post(description = "This is my first post"),
                    Post(description = "This is my second post"),
                )
            ),
            onFetchPosts = {},
            onAction = { _ ->

            })
    }
}