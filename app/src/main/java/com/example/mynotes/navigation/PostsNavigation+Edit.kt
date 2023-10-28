package com.example.mynotes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotes.myposts.PostForm
import com.example.mynotes.myposts.PostFormAction
import com.example.mynotes.myposts.PostsViewModel

fun NavGraphBuilder.editPost(
    navigationController: NavHostController,
    viewModel: PostsViewModel
) {
    composable(
        route = PostScreen.Edit().routeDefinition,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("description") {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        PostForm(
            id = it.arguments?.getString("id"),
            description = it.arguments?.getString("description"),
            action = PostFormAction.EDIT,
        ) { result ->
            if (result.action == PostFormAction.EDIT) {
                viewModel.update(result.post)
            }
            navigationController.popBackStack()
        }
    }
}