package com.example.mynotes.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.myposts.PostsViewModel

@Composable
fun PostsFlow() {
    val navigationController = rememberNavController()
    val viewModel = viewModel<PostsViewModel>()
    NavHost(navController = navigationController, startDestination = "main") {
        main(navigationController, viewModel)
        addPost(navigationController, viewModel)
        editPost(navigationController, viewModel)
        detail()
    }
}