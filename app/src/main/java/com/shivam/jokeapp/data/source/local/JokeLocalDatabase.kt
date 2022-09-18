package com.shivam.jokeapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shivam.jokeapp.data.models.Joke

@Database(entities = [Joke::class], version = 1, exportSchema = false)
abstract class JokeLocalDatabase : RoomDatabase() {
    abstract fun getJokeDao(): JokeDao
}