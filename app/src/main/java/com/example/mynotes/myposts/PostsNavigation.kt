package com.example.mynotes.myposts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun PostsFlow() {
    val navigationController = rememberNavController()
    var state by remember {
        mutableStateOf(PostsListState(posts = emptyList()))
    }
    NavHost(navController = navigationController, startDestination = "main") {
        composable(route = "main") {
            MainLayout(state = state) {
                navigationController.navigate("add")
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
    }
}