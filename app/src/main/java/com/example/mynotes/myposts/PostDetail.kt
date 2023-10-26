package com.example.mynotes.myposts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.ui.theme.MyNotesTheme

@Composable
fun PostDetail(modifier: Modifier = Modifier, description: String) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(20.dp)
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Divider(modifier = modifier.background(Color.Gray))
        Text(
            text = description,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .verticalScroll(state = rememberScrollState())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailsPreview() {
    MyNotesTheme {
        PostDetail(
            description = "This is the body of my post.\n\nThis is the second line."
        )
    }
}