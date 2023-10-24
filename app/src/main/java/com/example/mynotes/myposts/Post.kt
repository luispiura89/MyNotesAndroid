package com.example.mynotes.myposts

import java.util.UUID

data class Post(
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = ""
)