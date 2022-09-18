package com.shivam.jokeapp.di

import android.content.Context
import androidx.room.Room
import com.shivam.jokeapp.data.source.local.JokeDao
import com.shivam.jokeapp.data.source.local.JokeLocalDatabase
import com.shivam.jokeapp.data.source.remote.JokeAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): JokeLocalDatabase {
        return Room.databaseBuilder(
            appContext,
            JokeLocalDatabase::class.java,
            "JokesDB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideJokeDao(database: JokeLocalDatabase): JokeDao {
        return database.getJokeDao()
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideJokeAPI(client: Retrofit): JokeAPI {
        return client.create(JokeAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://geek-jokes.sameerkumar.website")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}