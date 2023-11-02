package com.example.mynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.myposts.composables.PostListAction
import com.example.mynotes.myposts.composables.PostsListState

@Composable
fun PostsFlow(state: PostsListState, onAction: (PostListAction) -> Unit) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = "main") {
        main(navigationController, state, onAction)
        addPost(navigationController, onAction)
        editPost(navigationController, onAction)
        detail()
    }
}