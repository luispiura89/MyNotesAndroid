package com.example.mynotes.myposts.coroutines

import android.util.Log
import com.example.mynotes.myposts.Post
import kotlinx.coroutines.delay

class MyAsyncClass {

    suspend fun firstCall(): String {
        Log.d("TEST_", "Starting first call")
        delay(1000L)
        return "This is the first call"
    }
    suspend fun secondCall(): String {
        Log.d("TEST_", "Starting second call")
        delay(1000L)
        return "This is the second call"
    }

    suspend fun fetchPosts(): List<Post> {
        delay(1000L)
        return listOf(
            Post(
                title = "iOS vs Android",
                description = "Both platforms are fun to work on and have their pros and cons."
            ),
            Post(
                title = "F1 2023 World Championship",
                description = "RedBull Racing has been giving headaches to every other team they faced."
            )
        )
    }

}