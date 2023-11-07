package com.example.mynotes.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PostsApi {

    @GET("todos")
    suspend fun  getPosts(): List<RemotePost>

}

object PostsService {
    fun retrofitProvider(): PostsApi {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostsApi::class.java)
    }
}