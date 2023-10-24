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
            MainLayout(state = state,
                onAddPost = {
                    navigationController.navigate("add")
                },
                onSelectPost = {
                    navigationController.navigate("detail/${it.title}/${it.description}")
                },
                onDeletePost = { post ->
                    state = state.copy(
                        posts = state.posts.toMutableList().also {
                            it.remove(post)
                        }
                    )
                }
            ) { post ->
                navigationController.navigate("add?id=${post.id}&title=${post.title}&description=${post.description}")
            }
        }
        composable(
            route = "add?id={id}&title={title}&description={description}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("title") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("description") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val position = it.arguments?.getString("id")?.let {
                state.posts.indexOfFirst { post ->
                    post.id == UUID.fromString(it)
                }
            } ?: -1
//            val position = state.posts.indexOfFirst { post ->
//                post.id == UUID.fromString()
//            }
            PostForm(
                title = it.arguments?.getString("title"),
                description = it.arguments?.getString("description"),
                action = if (it.arguments?.getString("title") == null) PostFormAction.ADD else PostFormAction.EDIT,
                position = position
            ) { result ->
                state = state.copy(posts = state.posts.toMutableList().also { list ->
                    if (result.action == PostFormAction.ADD) {
                        list.add(0, element = result.post)
                    } else {
                        list[result.position] = result.post
                    }
                })
                navigationController.popBackStack()
            }
        }
        composable(
            route = "detail/{title}/{description}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("description") {
                    type = NavType.StringType
                }
            )
        ) {
            PostDetail(
                title = it.arguments?.getString("title") ?: "",
                description = it.arguments?.getString("description") ?: ""
            )
        }
    }
}