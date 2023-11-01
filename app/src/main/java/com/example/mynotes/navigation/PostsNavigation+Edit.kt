package com.example.mynotes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotes.myposts.composables.PostForm
import com.example.mynotes.myposts.composables.PostFormAction
import com.example.mynotes.myposts.PostsViewModel
import java.util.Date

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
            },
            navArgument("createdOn") {
                type = NavType.LongType
                defaultValue = Date().time
            },
            navArgument("isComplete") {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) {
        PostForm(
            id = it.arguments?.getString("id"),
            description = it.arguments?.getString("description"),
            createdOn = it.arguments?.getLong("createdOn"),
            isComplete = it.arguments?.getBoolean("isComplete"),
            action = PostFormAction.EDIT
        ) { result ->
            if (result.action == PostFormAction.EDIT) {
                viewModel.update(result.post)
            }
            navigationController.popBackStack()
        }
    }
}