package com.example.mynotes.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PostsApi {

    @GET("todos")
    suspend fun  getPosts(): List<RemotePost>

}

object PostsService {
    fun retrofitProvider(): PostsApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PostsApi::class.java)
    }
}

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorizeRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer dummy_token")
            .build()
        /*
        If a refresh token is needed, you would need to inject the auth service and
        get the new token inside the `runBlocking` block
        val refreshedToken = runBlocking {
                // apiService is a retrofit service created with Retrofit.Builder()
            val response = apiService.refreshAccessToken()
            response.accessToken
         }

            if (refreshedToken != null) {
                // Create a new request with the refreshed access token
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken")
                    .build()

                // Retry the request with the new access token
                return chain.proceed(newRequest)
            }
         */
        Log.d("REQUEST_INTERCEPTED", "${authorizeRequest.header("Authorization")}")
        return chain.proceed(authorizeRequest)
    }

}