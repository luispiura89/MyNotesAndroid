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

    suspend fun fetchPostsFirstPage(): List<Post> {
        delay(2000L)
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

    suspend fun fetchPostsSecondPage(): List<Post> {
        delay(2000L)
        return listOf(
            Post(
                title = "SwiftUI vs Jetpack Compose",
                description = "They are very similar so far. I would say that I " +
                        "feel easier the separation of concerns when it comes to SwiftUI. " +
                        "Nevertheless, Jetpack Compose navigation seems easier"
            )
        )
    }

}