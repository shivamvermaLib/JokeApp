package com.shivam.jokeapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "jokes", indices = [Index(value = ["title"], unique = true)])
data class Joke(
    @PrimaryKey var id: Int? = null,
    @ColumnInfo var title: String
)
