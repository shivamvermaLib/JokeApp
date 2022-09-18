package com.shivam.jokeapp.di

import com.shivam.jokeapp.data.repository.JokeRepository
import com.shivam.jokeapp.data.repository.JokeRepositoryImpl
import com.shivam.jokeapp.data.source.local.JokeDao
import com.shivam.jokeapp.data.source.remote.JokeAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoRepository(
        remoteSource: JokeAPI,
        todoDao: JokeDao,
        dispatcher: CoroutineDispatcher
    ): JokeRepository {
        return JokeRepositoryImpl(remoteSource, todoDao, dispatcher)
    }
}