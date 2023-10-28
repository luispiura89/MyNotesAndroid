package com.example.mynotes.navigation

sealed class PostScreen {
    object Add: PostScreen()

    object Main: PostScreen()

    data class Edit(
        val id: String = "",
        val description: String = ""
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
                "edit/${this.id}/${this.description}"
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
                "edit/{id}/{description}"
            }
            is Detail -> {
                "detail/{description}"
            }
        }
}
