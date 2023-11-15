package com.example.mynotes.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.myposts.PostsViewModel
import com.example.mynotes.myposts.composables.PostListAction
import com.example.mynotes.myposts.composables.PostsListState

@Composable
fun PostsFlow() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = "main") {
        main(navigationController)
        addPost(navigationController)
        editPost(navigationController)
        detail()
    }
}