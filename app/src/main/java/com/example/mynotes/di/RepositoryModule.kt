package com.example.mynotes.di

import com.example.mynotes.api.PostRepositoryImpl
import com.example.mynotes.api.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(postsRepositoryImp: PostRepositoryImpl): PostsRepository
}