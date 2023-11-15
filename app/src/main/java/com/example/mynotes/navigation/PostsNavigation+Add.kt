package com.example.mynotes.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mynotes.myposts.PostsViewModel
import com.example.mynotes.myposts.composables.PostForm
import com.example.mynotes.myposts.composables.PostFormAction
import com.example.mynotes.myposts.composables.PostListAction

fun NavGraphBuilder.addPost(
    navigationController: NavHostController
) {
    composable(route = PostScreen.Add.routeDefinition) {
        val viewModel = hiltViewModel<PostsViewModel>()
        val onAction = viewModel::handle
        PostForm(
            id = null,
            description = null,
            createdOn = null,
            action = PostFormAction.ADD,
        ) { result ->
            if (result.action == PostFormAction.ADD) {
                onAction(PostListAction.Add(post = result.post))
            }
            navigationController.popBackStack()
        }
    }
}