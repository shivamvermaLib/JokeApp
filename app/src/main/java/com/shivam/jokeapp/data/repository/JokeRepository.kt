package com.shivam.jokeapp.data.repository

import com.shivam.jokeapp.data.models.Joke
import com.shivam.jokeapp.data.source.local.JokeDao
import com.shivam.jokeapp.data.source.remote.JokeAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface JokeRepository {
    fun getAllJokes(): Flow<List<Joke>>
    suspend fun fetchNewJokes(doRemoveOld: Boolean)
}

class JokeRepositoryImpl(
    private val jokeAPI: JokeAPI,
    private val jokeDao: JokeDao,
    private val dispatcher: CoroutineDispatcher,
) : JokeRepository {

    override fun getAllJokes(): Flow<List<Joke>> = jokeDao.getAllJokes()

    override suspend fun fetchNewJokes(doRemoveOld: Boolean): Unit = withContext(dispatcher) {
        println("JokeRepositoryImpl.fetchNewJokes>>")
        val response = jokeAPI.getJokes()
        println("Response:$response")
        if (response.isSuccessful) {
            response.body()?.let {
                if (doRemoveOld) {
                    jokeDao.deleteOldestJoke()
                }
                jokeDao.insertJoke(Joke(title = it))
            }
        } else {
            throw Exception(response.errorBody()?.stringSuspending(dispatcher))
        }
    }

}