package com.shivam.jokeapp.data.source.remote

import retrofit2.Response
import retrofit2.http.GET

interface JokeAPI {
    @GET("/api")
    suspend fun getJokes(): Response<String>
}