package com.aiyu.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aiyu.core.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "News"
    }

    abstract fun getNewsDao(): NewsDao
}