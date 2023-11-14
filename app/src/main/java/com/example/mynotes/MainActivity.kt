package com.example.mynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.myposts.MainLayout
import com.example.mynotes.myposts.Post
import com.example.mynotes.myposts.PostsViewModel
import com.example.mynotes.navigation.PostsFlow
import com.example.mynotes.myposts.composables.PostsListState
import com.example.mynotes.ui.theme.MyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    // Room database creation
//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            LocalPostDataBase::class.java,
//            "posts.db"
//        ).build()
//    }
//    /*
//    Given this view model receives the dao as a parameter the only way to instantiate it
//    is with a `factoryProducer` in order to inject the dao
//     */
//    private val viewModel by viewModels<PostsViewModel>(
//        factoryProducer = {
//            object: ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return PostsViewModel(db.localPostsDao, PostsService.retrofitProvider()) as T
//                }
//            }
//        }
//    )

    private val viewModel: PostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                PostsFlow(viewModel.uiState.collectAsState().value, viewModel::handle)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme {
        MainLayout(
            state = PostsListState(
                posts = listOf(
                    Post(description = "This is my first post"),
                    Post(description = "This is my second post"),
                )
            ),
            onFetchPosts = {},
            onAction = { _ ->

            })
    }
}