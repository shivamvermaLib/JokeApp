package com.shivam.jokeapp.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending(dispatcher: CoroutineDispatcher) =
    withContext(dispatcher) { string() }
