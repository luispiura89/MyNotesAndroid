package com.example.mynotes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mynotes.myposts.composables.PostForm
import com.example.mynotes.myposts.composables.PostFormAction
import com.example.mynotes.myposts.composables.PostListAction

fun NavGraphBuilder.addPost(
    navigationController: NavHostController,
    onAction: (PostListAction) -> Unit
) {
    composable(route = PostScreen.Add.routeDefinition) {
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