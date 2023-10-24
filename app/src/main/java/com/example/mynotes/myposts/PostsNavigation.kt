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

@Composable
fun PostsFlow() {
    val navigationController = rememberNavController()
    var state by remember {
        mutableStateOf(PostsListState(posts = emptyList()))
    }
    NavHost(navController = navigationController, startDestination = "main") {
        composable(route = "main") {
            MainLayout(state = state, onAddPost = {
                navigationController.navigate("add")
            }) {
                navigationController.navigate("detail/${it.title}/${it.description}")
            }
        }
        composable(route = "add") {
            PostForm { post ->
                state = state.copy(posts = state.posts.toMutableList().also {
                    it.add(0, element = post)
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