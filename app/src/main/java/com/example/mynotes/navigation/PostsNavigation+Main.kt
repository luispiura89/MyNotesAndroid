package com.example.mynotes.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mynotes.myposts.MainLayout
import com.example.mynotes.myposts.PostListAction
import com.example.mynotes.myposts.PostsViewModel

fun NavGraphBuilder.main(
    navigationController: NavHostController,
    viewModel: PostsViewModel
) {
    composable(route = PostScreen.Main.routeDefinition) {
        MainLayout(
            state = viewModel.uiState.collectAsState().value,
            onFetchPosts = { posts ->
                viewModel.add(posts)
            }
        ) { action ->
            when (action) {
                PostListAction.Add -> {
                    navigationController.navigate(PostScreen.Add.routeDefinition)
                }
                is PostListAction.Remove -> {
                    viewModel.remove(action.post)
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
                            description = action.post.description)
                            .route
                    )
                }
            }
        }
    }
}