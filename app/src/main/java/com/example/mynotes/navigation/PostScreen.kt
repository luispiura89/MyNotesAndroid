package com.example.mynotes.navigation

import java.util.Date

sealed class PostScreen {
    object Add: PostScreen()

    object Main: PostScreen()

    data class Edit(
        val id: String = "",
        val description: String = "",
        val createdOn: Date = Date(),
        val isComplete: Boolean = false
    ): PostScreen()
    data class Detail(
        val description: String = ""
    ): PostScreen()

    val route: String
        get() = when(this) {
            is Main -> {
                "main"
            }
            is Add -> {
                "add"
            }
            is Edit -> {
                "edit/${this.id}/${this.description}/${this.createdOn.time}/${this.isComplete}"
            }
            is Detail -> {
                "detail/${this.description}"
            }
        }
    val routeDefinition: String
        get() = when(this) {
            is Main -> {
                "main"
            }
            is Add -> {
                "add"
            }
            is Edit -> {
                "edit/{id}/{description}/{createdOn}/{isComplete}"
            }
            is Detail -> {
                "detail/{description}"
            }
        }
}
