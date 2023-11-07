package com.example.mynotes.api

data class RemotePost(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)