package com.example.mynotes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class LocalPost(
    val description: String,
    val isComplete: Boolean,
    val creationDate: Long = Date().time,
    @PrimaryKey
    val id: String
)