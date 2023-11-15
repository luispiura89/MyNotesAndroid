package com.example.mynotes.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    indices = [
        Index(value = ["postId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalPost::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("postId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalPostChangeLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val changeType: String,
    val postId: String,
    val createdOn: Long = Date().time
)