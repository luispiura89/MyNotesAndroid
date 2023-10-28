package com.example.mynotes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotes.myposts.PostDetail

fun NavGraphBuilder.detail() {
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