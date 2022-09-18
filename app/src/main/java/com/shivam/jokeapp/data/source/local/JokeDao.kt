package com.shivam.jokeapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shivam.jokeapp.data.models.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Query("select * from jokes")
    fun getAllJokes(): Flow<List<Joke>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Query("delete from jokes where id=(select id from jokes order by id asc limit 1)")
    suspend fun deleteOldestJoke()
}