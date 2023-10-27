package com.example.mynotes.myposts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.UUID

enum class PostFormAction {
    EDIT,
    ADD
}

data class PostFormResult(
    val action: PostFormAction,
    val post: Post
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostForm(
    id: String?,
    description: String?,
    modifier: Modifier = Modifier,
    action: PostFormAction,
    onPostAction: (PostFormResult) -> Unit
) {
    var descriptionState by remember {
        mutableStateOf(description ?: "")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = descriptionState,
                onValueChange = { descriptionState = it },
                label = {
                    Text("Description")
                }
            )
            Spacer(modifier = modifier.height(20.dp))
            Button(onClick = {
                onPostAction(
                    PostFormResult(
                        action,
                        post = Post(
                            id = id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
                            description = descriptionState
                        )
                    )
                )
            }) {
                Text(text = "Save Post")
            }
        }
    }
}