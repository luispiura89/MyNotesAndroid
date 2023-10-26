package com.example.mynotes.myposts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.util.UUID

@Composable
fun PostsFlow() {
    val navigationController = rememberNavController()
    var state by remember {
        mutableStateOf(PostsListState(posts = emptyList()))
    }
    NavHost(navController = navigationController, startDestination = "main") {
        composable(route = "main") {
            MainLayout(
                state = state,
                onFetchPosts = { posts ->
                    state = state.copy(posts = state.posts.toMutableList().also {
                        it.addAll(0, posts)
                    })
                }
            ) { action ->
                when (action) {
                    PostListAction.Add -> {
                        navigationController.navigate("form")
                    }
                    is PostListAction.Remove -> {
                        state = state.copy(
                            posts = state.posts.toMutableList().also {
                                it.remove(action.post)
                            }
                        )
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
                description = it.arguments?.getString("description"),
                action = if (it.arguments?.getString("description") == null) PostFormAction.ADD else PostFormAction.EDIT,
            ) { result ->
                state = state.copy(posts = state.posts.toMutableList().also { list ->
                    if (result.action == PostFormAction.ADD) {
                        list.add(0, element = result.post)
                    } else {
                        val position = it.arguments?.getString("id")?.let {
                            state.posts.indexOfFirst { post ->
                                post.id == UUID.fromString(it)
                            }
                        } ?: -1
                        list[position] = result.post
                    }
                })
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