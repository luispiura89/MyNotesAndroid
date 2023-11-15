package com.example.mynotes.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mynotes.myposts.MainLayout
import com.example.mynotes.myposts.PostsViewModel
import com.example.mynotes.myposts.composables.PostListAction

fun NavGraphBuilder.main(
    navigationController: NavHostController
) {
    composable(route = PostScreen.Main.routeDefinition) {
        val viewModel = hiltViewModel<PostsViewModel>()
        val onAction = viewModel::handle
        MainLayout(
            state = viewModel.uiState.collectAsState().value,
            onFetchPosts = {
                onAction(PostListAction.FetchPosts)
            }
        ) { action ->
            when (action) {
                is PostListAction.Add -> {
                    navigationController.navigate(PostScreen.Add.routeDefinition)
                }
                is PostListAction.Select -> {
                    navigationController.navigate(
                        PostScreen.Detail(description = action.post.description).route
                    )
                }
                is PostListAction.Edit -> {
                    navigationController.navigate(
                        PostScreen.Edit(
                            id = action.post.id.toString(),
                            description = action.post.description,
                            createdOn = action.post.createdOn,
                            isComplete = action.post.isComplete
                        )
                            .route
                    )
                }
                else -> {
                    onAction(action)
                }
            }
        }
    }
}