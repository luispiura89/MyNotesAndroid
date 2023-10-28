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
    composable(route = "main") {
        MainLayout(
            state = viewModel.uiState.collectAsState().value,
            onFetchPosts = { posts ->
                viewModel.add(posts)
            }
        ) { action ->
            when (action) {
                PostListAction.Add -> {
                    navigationController.navigate("add")
                }
                is PostListAction.Remove -> {
                    viewModel.remove(action.post)
                }
                is PostListAction.Select -> {
                    navigationController.navigate("detail/${action.post.description}")
                }
                is PostListAction.Edit -> {
                    navigationController.navigate(
                        "edit/${action.post.id}/${action.post.description}"
                    )
                }
            }
        }
    }
}