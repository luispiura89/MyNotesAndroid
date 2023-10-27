package com.example.mynotes.myposts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun PostsFlow() {
    val navigationController = rememberNavController()
    val viewModel = viewModel<PostsViewModel>()
    NavHost(navController = navigationController, startDestination = "main") {
        composable(route = "main") {
            MainLayout(
                state = viewModel.uiState.collectAsState().value,
                onFetchPosts = { posts ->
                    viewModel.add(posts)
                }
            ) { action ->
                when (action) {
                    PostListAction.Add -> {
                        navigationController.navigate("form")
                    }
                    is PostListAction.Remove -> {
                        viewModel.remove(action.post)
                    }
                    is PostListAction.Select -> {
                        navigationController.navigate("detail/${action.post.description}")
                    }
                    is PostListAction.Edit -> {
                        navigationController.navigate(
                            "form?id=${action.post.id}&description=${action.post.description}"
                        )
                    }
                }
            }
        }
        composable(
            route = "form?id={id}&title={title}&description={description}",
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
                action = if (it.arguments?.getString("description") == null) PostFormAction.ADD else PostFormAction.EDIT,
            ) { result ->
                when (result.action) {
                    PostFormAction.ADD -> {
                        viewModel.add(result.post)
                    }
                    else -> {
                        viewModel.update(result.post)
                    }
                }
                navigationController.popBackStack()
            }
        }
        composable(
            route = "detail/{description}",
            arguments = listOf(
                navArgument("description") {
                    type = NavType.StringType
                }
            )
        ) {
            PostDetail(
                description = it.arguments?.getString("description") ?: ""
            )
        }
    }
}