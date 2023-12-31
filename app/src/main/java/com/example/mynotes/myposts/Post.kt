package com.example.mynotes.myposts

import java.util.Date
import java.util.UUID

data class Post(
    val id: UUID = UUID.randomUUID(),
    val description: String = "",
    val isComplete: Boolean = false,
    var createdOn: Date = Date()
)