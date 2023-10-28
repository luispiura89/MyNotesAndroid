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
        composable(
            route = "edit/{id}/{description}",
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