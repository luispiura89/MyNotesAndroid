package com.example.mynotes.di

import android.content.Context
import androidx.room.Room
import com.example.mynotes.database.LocalPostDataBase
import com.example.mynotes.database.LocalPostsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): LocalPostDataBase {
        return Room.databaseBuilder(
            appContext,
            LocalPostDataBase::class.java,
            "posts.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(database: LocalPostDataBase): LocalPostsDao {
        return database.localPostsDao
    }
}