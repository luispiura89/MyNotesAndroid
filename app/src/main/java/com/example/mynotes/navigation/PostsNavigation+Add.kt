package com.example.mynotes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mynotes.myposts.PostForm
import com.example.mynotes.myposts.PostFormAction
import com.example.mynotes.myposts.PostsViewModel

fun NavGraphBuilder.addPost(
    navigationController: NavHostController,
    viewModel: PostsViewModel
) {
    composable(route = "add") {
        PostForm(
            id = null,
            description = null,
            action = PostFormAction.ADD,
        ) { result ->
            if (result.action == PostFormAction.ADD) {
                viewModel.add(result.post)
            }
            navigationController.popBackStack()
        }
    }
}